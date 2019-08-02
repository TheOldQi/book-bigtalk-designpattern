package com.qixiafei.bookbigtalkdesignpattern;

/**
 * <P>Description: . </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE AT: 2019/3/26 18:29</P>
 * <P>UPDATE AT: 2019/3/26 18:29</P>
 *
 * @author 齐霞飞
 * @version 1.0
 * @since java 1.8.0
 */
class Test {

    public static void main(String[] args) throws InterruptedException {
        int cycle = Integer.MAX_VALUE;
        System.out.println("current cycle times = " + cycle);
        concurrent(cycle);
        sync(cycle);
//        for (; ; ) {
//
//        }

    }


    public static void concurrent(int cycle) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(new Runnable() {
            int a = 0;

            @Override
            public void run() {
                for (int i = 0; i < cycle; i++) {
                    a++;
                }
            }
        });
        t1.start();
        int b = 0;
        for (int i = 0; i < cycle; i++) {
            b++;
        }
        t1.join();
        System.out.println("concurrent elapse :" + (System.currentTimeMillis() - start) + ",b=" + b);
    }


    public static void sync(int cycle) {
        long start = System.currentTimeMillis();
        int a = 0;
        int b = 0;
        for (int i = 0; i < cycle; i++) {
            a++;
        }
        for (int i = 0; i < cycle; i++) {
            b++;
        }
        System.out.println("sync elapse :" + (System.currentTimeMillis() - start) + " a=" + a + ",b=" + b);
    }
}
