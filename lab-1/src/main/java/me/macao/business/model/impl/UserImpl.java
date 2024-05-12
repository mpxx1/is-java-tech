package me.macao.business.model.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.macao.business.exception.PasswdCreateException;
import me.macao.business.model.enums.UserAccountType;
import me.macao.business.model.interfaces.*;
import me.macao.business.repository.impl.MessageRepositoryImpl;
import me.macao.business.repository.interfaces.MessageRepository;

import java.util.Collection;
import java.util.Random;
/**
 * A class representing a user.
 */
@Getter
public class UserImpl implements User {

  private final long id;
  @NonNull
  private final String name;
  @NonNull
  private final String surname;
  @Setter
  private Address address;
  private short passwd;
  @Setter
  private boolean subscribedToUpdates;
  private Long passport;
  private UserAccountType userAccountType = UserAccountType.DOUBTFUL;
  private final MessageRepository messageRepo = new MessageRepositoryImpl();

  /**
   * Constructs a new user.
   *
   * @param name the user's name.
   * @param surname the user's surname.
   * @param address the user's address.
   * @param passport the user's passport number.
   */
  public UserImpl(@NonNull String name, @NonNull String surname, Address address, Long passport) {
    this.name = name;
    this.surname = surname;
    this.address = address;
    this.passport = passport;

    Random rand = new Random();

    this.id = Math.abs(rand.nextLong());
    short passwdTmp = (short) rand.nextInt();
    this.passwd = (short) Math.abs(passwdTmp);

    if (passport != null) {
      userAccountType = UserAccountType.TRUSTED;
    }
  }

  @Override
  public void setPasswd(short passwd) throws PasswdCreateException {
    if (passwd <= 1000) {
      throw new PasswdCreateException();
    }

    this.passwd = passwd;
  }

  @Override
  public void setPassport(@NonNull Long passport) {
    this.passport = passport;
    userAccountType = UserAccountType.TRUSTED;
  }

  public void sendMessage(Message message) {
    messageRepo.add(message);
  }

  @Override
  public @NonNull Collection<Message> getMessages() {
    return messageRepo.all();
  }

  @Override
  public void clearMessages() {
    messageRepo.clear();
  }
}

