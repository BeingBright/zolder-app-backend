package nl.brighton.zolder.resource.exception;

public class DuplicateKey extends Exception{

  public DuplicateKey(String key) {
    super(String.format("Duplicate Item '%s'",key));
  }
}
