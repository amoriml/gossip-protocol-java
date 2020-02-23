package br.com.ufabc.GossipProtocol.util;

import java.net.*;

public abstract class PacoteFactory {

    public static DatagramPacket createPackage(String message, String destinyHost, int destinyPort) throws UnknownHostException {
        return new DatagramPacket(
                message.getBytes(),
                message.getBytes().length,
                InetAddress.getByName(destinyHost),
                destinyPort
        );
    }

    public static DatagramPacket createPackage(String message) {
        return new DatagramPacket(message.getBytes(), message.getBytes().length);
    }

    public static DatagramPacket createPackage(byte[] buffer) {
        return new DatagramPacket(buffer, buffer.length);
    }

    public static DatagramSocket createSocket() throws SocketException {
        return new DatagramSocket();
    }

    public static DatagramSocket createSocket(int port) throws SocketException {
        return new DatagramSocket(port);
    }
}
