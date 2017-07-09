package com.spnotes.kafka.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by sunilpatil on 12/28/15.
 */
public class Producer {
    private static Scanner in;
    public static void main(String[] argv)throws Exception {
        if (argv.length != 1) {
            System.err.println("Please specify 1 parameters ");
            System.exit(-1);
        }
        String topicName = argv[0];
        in = new Scanner(System.in);
        System.out.println("Enter message(type exit to quit)");

        //Configure the Producer
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.ByteArraySerializer");
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

        org.apache.kafka.clients.producer.Producer producer = new KafkaProducer(configProperties);
        String currentLine = null;

        String target_dir = "/Users/shuvamoymondal/Test";
        File dir = new File(target_dir);
        File[] files = dir.listFiles();

        for (File f : files) {
            if(f.isFile()) {
                BufferedReader inputStream = null;

                try {
                    inputStream = new BufferedReader(
                            new FileReader(f));
                    String line;

                    while ((line = inputStream.readLine()) != null) {

                    ProducerRecord<String, String> rec = new ProducerRecord<String, String>(topicName,line);
                            producer.send(rec);



                       // System.out.println(line);
                    }
                }
                finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }
        }


        in.close();

        producer.close();
    }
}

