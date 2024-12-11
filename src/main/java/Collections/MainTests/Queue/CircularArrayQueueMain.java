package Collections.MainTests.Queue;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Queues.*;

/**
 * CircularArrayQueueMain tests the CircularArrayQueue class.
 *
 * @author ESTG LSIRC 8230367 - Diogo Pereira Batista
 * @Author ESTG LSIRC 8190315 - Rodrigo Fernandes Ribeiro
 */
public class CircularArrayQueueMain {

    /**
     * Encrypts a message using the Caesar cipher.
     *
     * @param message the message to be encrypted
     * @param key     the key to encrypt the message
     * @return the encrypted message
     * @throws EmptyCollectionException if the collection is empty
     */
    public static String encrypt(String message, int[] key) throws EmptyCollectionException {

        int keySize = key.length;

        QueueADT<Integer> keyQueue = new CircularArrayQueue<>();
        QueueADT<Character> result = new CircularArrayQueue<>();

        for (int i = 0; i < keySize; i++) {
            keyQueue.enqueue(key[i]);
        }

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);  // Pega o caractere atual da mensagem

            if (Character.isLetter(c)) {
                int deslocamento = keyQueue.dequeue();
                keyQueue.enqueue(deslocamento);

                char base = Character.isUpperCase(c) ? 'A' : 'a';

                char encodedChar = (char) ((c - base + deslocamento) % 26 + base);
                result.enqueue(encodedChar);
            } else {
                result.enqueue(c);
            }
        }
        return result.toString();
    }

    /**
     * Main method for testing the encrypt method.
     *
     * @param args command line arguments (not used)
     * @throws EmptyCollectionException if the collection is empty
     */
    public static void main(String[] args) throws EmptyCollectionException {
        String message = "knowledge is power";
        int[] key = {3, 1, 7, 4, 2};  // Exemplo de chave

        // Codifica a mensagem
        String encodedMessage = encrypt(message, key);

        // Exibe a mensagem codificada
        System.out.println("Mensagem Codificada: " + encodedMessage);
    }

}
