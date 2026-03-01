package com.tpservers.Repositories;

public final class SecureRespository {

    private SecureRespository() {
    }

    private static class Holder {
        private static final SecureRespository INSTANCE = new SecureRespository();
    }

    public static SecureRespository getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Lấy phần đuôi (tail) của chuỗi identifier với độ dài cố định.
     *
     * Logic:
     * - Strip ký tự không phải alphanumeric
     * - Nếu chuỗi quá ngắn / rỗng so với yêu cầu tham số len:
     * -> fallback sang hashCode() nội bộ
     * - Trả về chuỗi có độ dài tối đa = len
     *
     * @param s   Chuỗi gốc (CPU ID / Disk serial)
     * @param len Số ký tự mong muốn
     */
    public static String hwidTailCuttingEncode(String fullString, int cutLen) {
        if (fullString.isEmpty() || fullString.length() < cutLen) {
            /*
             * Fallback:
             * - Dùng hashCode() để đảm bảo có giá trị ổn định
             * - Không đảm bảo unique tuyệt đối (chấp nhận được cho agent identity)
             * - Nhanh, không tốn CPU
             */
            int hash = Math.abs(fullString.hashCode());
            fullString = "k" + hash;
        } else {
            // Chuẩn hóa chuỗi (loại bỏ ký tự đặc biệt)
            fullString = fullString.replaceAll("[^A-Za-z0-9]", "");
        }

        // Trả về phần đuôi có độ dài = len
        return fullString.length() < cutLen
                ? fullString
                : fullString.substring(fullString.length() - cutLen);
    }
}
