package io.github.bael.dictionary.client;

/**
 * This is a dictionary client. It can add, get, or delete term definitions of Dictionary.
 */
class Client {


    public Client(String[] args) {


    }

    /**
     * main method
     *
     * @param args arguments of program from command line<p>
     *             Preconditions: args[0] - valid ip, args[1] port for successful connection <br>
     *             args[2] - command to complete after connection <br>
     *             args[3] - term to process <br>
     *             args[4] - definitions of term <br>
     *             </p>
     */
    public static void main(String[] args) {


        if (args.length == 0) {

            System.out.format("Укажите корректные параметры запуска:%n 1) %s %n 2) %s %n 3) %s %n 4) %s %n 5) %s %n %s %n",
                    "IP адрес сервера",
                    "Порт сервера",
                    "Название команды, допустимые значения add get remove",
                    "Термин, который вы хотите обработать командой",
                    "Значения термина (в случае команды add)",
                    "Пример корректного запуска: java -jar client.jar 192.168.0.1 9000 add hello алло привет здравствуйте");
            return;
        }


    }


}
