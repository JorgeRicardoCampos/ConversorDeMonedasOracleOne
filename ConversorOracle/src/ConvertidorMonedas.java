import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConvertidorMonedas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("*** Escriba el número de la opción deseada ***");
            System.out.println("1 - Dolar =>> Peso Mexicano");
            System.out.println("2 - Peso Mexicano =>> Dolar");
            System.out.println("3 - Dolar =>> Real Brasileño");
            System.out.println("4 - Real Brasileño =>> Dolar");
            System.out.println("5 - Dolar =>> Peso Colombiano");
            System.out.println("6 - Peso Colombiano =>> Dolar");
            System.out.println("7 - Salir");
            System.out.print("Opción: ");

            int opcion = scanner.nextInt();
            if (opcion == 7) {
                System.out.println("¡Muchas gracias por utilizar nuestro Convertidor!");
                break;
            }

            switch (opcion) {
                case 1:
                    convertirMoneda("USD", "MXN", scanner);
                    break;
                case 2:
                    convertirMoneda("MXN", "USD", scanner);
                    break;
                case 3:
                    convertirMoneda("USD", "BRL", scanner);
                    break;
                case 4:
                    convertirMoneda("BRL", "USD", scanner);
                    break;
                case 5:
                    convertirMoneda("USD", "COP", scanner);
                    break;
                case 6:
                    convertirMoneda("COP", "USD", scanner);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }

    public static void convertirMoneda(String from, String to, Scanner scanner) {
        System.out.print("Ingrese la cantidad de " + from + " que desea convertir a " + to + ": ");
        double cantidad = scanner.nextDouble();

        try {
            URL url = new URL("https://v6.exchangerate-api.com/v6/7658e82a903bc8e781b1c4dd/latest/" + from);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            // Analizar la respuesta manualmente
            String response = content.toString();
            int startIndex = response.indexOf("\"" + to + "\":");
            if (startIndex != -1) {
                startIndex += to.length() + 3; // Avanzar al inicio del valor
                int endIndex = response.indexOf(",", startIndex);
                String rateString = response.substring(startIndex, endIndex);
                double rate = Double.parseDouble(rateString);
                double resultado = cantidad * rate;
                System.out.println(cantidad + " " + from + " = " + resultado + " " + to);
            } else {
                System.out.println("No se encontró el valor de conversión para " + to);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
