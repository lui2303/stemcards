public class test {
    public long numberOfPowerfulInt(long start, long finish, int limit, String s) {
        int startDigitCount = (int) (Math.log(start) + 1);
        int finishDigitCount = (int) (Math.log(finish) + 1);
        long ans = 0;

        StringBuilder startBuilder = new StringBuilder(String.valueOf(start));
        startBuilder.replace(startBuilder.length() - s.length(), startBuilder.length(), s);

        StringBuilder finishBuilder = new StringBuilder(String.valueOf(finish));

        if (!(Long.parseLong(startBuilder.toString()) > start && Long.parseLong(startBuilder.toString()) < finish))
            return 0;

        if(startDigitCount == finishDigitCount) {

            // handle the case where both numbers start with the same digit
            long tmp = Math.min(Integer.parseInt(String.valueOf(finishBuilder.charAt(0))) - 1, limit) - Integer.parseInt(String.valueOf(startBuilder.charAt(0)));
            if (tmp <= 0)
                return 0; // limit is lower than first start character plus the fact that finishDigits = startDigits imply that no valid powerful int can be formed

            tmp *= (long) Math.pow(limit + 1, Math.max(0, startDigitCount - 1 - s.length()));

            long tmp2 = 1;
            for (int j = 1; j < startDigitCount -s.length(); j++) {
                tmp2 *= Math.min(limit, Integer.parseInt(String.valueOf(finishBuilder.charAt(j)))) + 1;
            }

            ans += tmp2 + tmp;

        }



        for (int i = startDigitCount + 1; i < finishDigitCount; i++) {
            ans += (long) (Math.pow(limit + 1, i - 1 - s.length()) * limit);
        }

        return 0L;
    }
}
