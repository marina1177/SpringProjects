package parser.interfaces;

public interface Sender {

  int send(String key, String value);

  String getTopic();

  void setTopic(String topic);
}
