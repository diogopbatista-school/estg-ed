package Collections.MainTests.Queue;

import Collections.Exceptions.EmptyCollectionException;
import Collections.Queues.CircularArrayQueue;
import Collections.Queues.LinkedQueue;
import Collections.Queues.QueueADT;

public class FP4MAIN {
    public static void main(String[] args) {

        QueueADT<Integer> keyQueue = new LinkedQueue<>();
        QueueADT<Character> result = new LinkedQueue<>();

        QueueADT<Integer> keyQueue2 = new CircularArrayQueue<>();
        QueueADT<Character> result2 = new CircularArrayQueue<>();

        String message = "knowledge is power";
        int[] key = {3, 1, 7, 4, 2};  // Exemplo de chave

        String encodedMessage = encrypt(message, key, keyQueue, result);
        String encodedMessage2 = encrypt(message, key, keyQueue2, result2);

        System.out.println("Mensagem Codificada: " + encodedMessage);
        System.out.println("Mensagem Codificada: " + encodedMessage2);

    }

    public static String encrypt(String message, int[] key, QueueADT<Integer> keyQueue, QueueADT<Character> result) throws EmptyCollectionException {

        int keySize = key.length;

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
}
