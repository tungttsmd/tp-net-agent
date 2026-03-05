package com.tpservers.Services;

import com.tpservers.Repositories.MetaRespository;
import com.tpservers.Services.Facade.ConsoleService;
import com.tpservers.Services.Facade.HeartbeatService;
import com.tpservers.Services.Facade.MqttService;
import com.tpservers.Services.Facade.WorkerService;

public final class Service {

    private Service() {
    }

    private static class Holder {

        static boolean started = false;
        static final Service INSTANCE = new Service();
    }

    public static Service getInstance() {
        return Holder.INSTANCE;
    }

    public static void boot() {

        if (Holder.started) {
            ConsoleService.error("Services already started");
            return;
        }

        ConsoleService.info("════════════════════════════════════════");
        ConsoleService.info("  tp-net-agent  |  booting...");
        ConsoleService.info("════════════════════════════════════════");
        ConsoleService.breakLine();

        /* ========== MQTT ============ */
        ConsoleService.info("[1/2] Starting MQTT Service...");
        try {
            MqttService.start();
            ConsoleService.info("[1/2] MQTT Service — OK");
        } catch (Exception e) {
            ConsoleService.error("[1/2] MQTT Service — FAILED: " + e.getMessage());
        }
        ConsoleService.breakLine();

        /* ========== WORKER POOL ============ */
        ConsoleService.info("[2/2] Starting Worker Pool...");
        try {
            WorkerService.start();
            ConsoleService.info("[2/2] Worker Pool — OK  (workers: " + WorkerService.getWorkerCount() + ")");
        } catch (Exception e) {
            ConsoleService.error("[2/2] Worker Pool — FAILED: " + e.getMessage());
        }
        ConsoleService.breakLine();

        /* ========== HEARTBEAT ============ */
        HeartbeatService.start(12);

        /* ========== SUMMARY ============ */
        ConsoleService.info("════════════════════════════════════════");
        ConsoleService.info("  HOST FROM LOCAL IP      : " + MetaRespository.hostLocalIp());
        ConsoleService.info("  HOST ID                 : " + MetaRespository.hostId());
        ConsoleService.info("  HOST IDENT              : " + MqttService.clientId());
        ConsoleService.info("  THREADS                 : " + WorkerService.getWorkerCount());
        ConsoleService.info("════════════════════════════════════════");
        ConsoleService.breakLine();

        Holder.started = true;
    }
}
