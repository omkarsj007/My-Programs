class Type implements Token
{
    int choice;
    String type;
  
    public Type(String i)
    {
	type = i;
	choice = 0;
    }
  
    public String toString(int t)
    {
	String ret = "";
	ret = type;
	return ret;
    }

    public String typeCheck(VarTable table, int scope) throws ExampleException
    {
	return type;
    }
}
  
