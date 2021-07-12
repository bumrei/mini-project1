package net.mini.project1;

public class Main {

  public static void main(String[] args) throws Exception {
    Login lg = new Login();
    int controlThread = 0;
    System.out.println("                    ◤----------------◥ ");
    System.out.println("================= * | 단어 암기 게임 | * ===================");
    System.out.print("                    ◣----------------◢ ");
    Thread.sleep(controlThread);
    System.out.println("\n       *            *              *         *         *");
    Thread.sleep(controlThread);
    System.out.println("\n*         apple           *              *          Haha");
    Thread.sleep(controlThread);
    System.out.println("\n      Sweet           *        자동차             *");
    Thread.sleep(controlThread);
    System.out.println("\n    *        양초               *         Fly          *");
    Thread.sleep(controlThread);
    System.out.println("\n*           *         Amazing        *                장난감 ");
    Thread.sleep(controlThread);
    System.out.println("\n        Car             *                 Happy      *");
    Thread.sleep(controlThread);
    lg.executeLogin();
  }

}
