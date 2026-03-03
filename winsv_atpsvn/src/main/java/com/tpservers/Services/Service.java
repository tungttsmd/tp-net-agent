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

        if (Holder.started == true) {
            ConsoleService.error("Services already started");
            return;
        }

        ConsoleService.info("Booting services...");
        ConsoleService.breakLine();

        /* ========== TURN MQTT SERVICE ON ============ */
        try {
            MqttService.start();
            ConsoleService.info("MQTT Client ID: " + MqttService.clientId() + "\n" +
                    "[1/2] MQTT Service booted successfully");
        } catch (Exception e) {
            ConsoleService.error("[1/2] MQTT Service booted failed");
        }
        ConsoleService.breakLine();

        /* ========== TURN HEARTBEAT ============ */
        HeartbeatService.start(12);

        /* ========== TURN WORKER SERVICE ON ============ */
        try {
            WorkerService.start();
            ConsoleService.success("Worker pool count: " + WorkerService.getWorkerCount() + "\n" +
                    "[2/2] Worker pool service booted successfully");
        } catch (Exception e) {
            ConsoleService.error("[2/2] Worker pool service booted failed");
        }

        ConsoleService.breakLine();

        /* ========== INFOMATION ============ */
        ConsoleService.info("HOST ID: " + MetaRespository.hostId());
        ConsoleService.info("MQTT Client ID: " + MqttService.clientId());
        ConsoleService.info("WORKER COUNT: " + WorkerService.getWorkerCount());

        Holder.started = true;
        return;
    }
}
