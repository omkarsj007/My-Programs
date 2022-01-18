class Program implements Token
{
    String id;
    Memberdecls ms;
    VarTable table;
    public Program(String i, Memberdecls m)
  {
      id = i;
      ms = m;
      table = new VarTable();
  }

  public String toString(int t)
  {
    return ("Program:\n" + "class " + id + " {\n" + ms.toString(t+1) + "\n}\n");
  }
    
    public void typeCheck() throws ExampleException
    {
	System.out.println("IM in Program");
	ms.typeCheck(table,0);
    }
}
