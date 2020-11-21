package parser.entity;


import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "statistics")
public class Statistics {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  private Timestamp operationTime; // когда была инициирована операция

  @NotBlank
  private String topic; // в какой топик отправили данные

  @NotNull
  private int successLoadMessages; // сколько сообщений отправилось успешно

  @NotNull
  private int failLoadMessages; // сколько сообщений зафейлили
}

