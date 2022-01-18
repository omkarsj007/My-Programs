class Program implements Token
{
    String id;
    Memberdecls ms;
    public Program(String i, Memberdecls m)
  {
      id = i;
      ms = m;
  }

  public String toString(int t)
  {
    return ("Program:\n" + "class " + id + " {\n" + ms.toString(t+1) + "\n}\n");
  }
}
