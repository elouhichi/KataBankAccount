package Kata.Bank.account.domain;

public class EnoughException extends Exception{
    public EnoughException() {
        super("pas assez de solde pour retirer!");
    }
}
