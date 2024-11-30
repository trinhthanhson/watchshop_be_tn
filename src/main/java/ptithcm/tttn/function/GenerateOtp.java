package ptithcm.tttn.function;

import java.security.SecureRandom;

public class GenerateOtp {

    public static String generateOTP() {
        // Danh sách các ký tự hợp lệ
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*()-_+=<>?";
        String allChars = upperCase + lowerCase + digits + specialChars;

        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        // Đảm bảo mã OTP có ít nhất 1 ký tự từ mỗi nhóm
        otp.append(upperCase.charAt(random.nextInt(upperCase.length())));
        otp.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        otp.append(digits.charAt(random.nextInt(digits.length())));
        otp.append(specialChars.charAt(random.nextInt(specialChars.length())));

        // Thêm các ký tự ngẫu nhiên từ toàn bộ danh sách để đạt tổng cộng 8 ký tự
        for (int i = 4; i < 8; i++) { // Bắt đầu từ 4 vì đã thêm 4 ký tự trước đó
            otp.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Trộn ngẫu nhiên thứ tự các ký tự trong mã OTP
        char[] otpArray = otp.toString().toCharArray();
        for (int i = otpArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = otpArray[index];
            otpArray[index] = otpArray[i];
            otpArray[i] = temp;
        }

        return new String(otpArray);
    }


}
