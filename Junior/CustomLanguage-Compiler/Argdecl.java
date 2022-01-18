class Argdecl implements Token
{
    int choice;
    Type ty;
    String id;

    public Argdecl(Type t, String i)
    {
        ty = t;
        id = i;
        choice = 0;
    }

    public Argdecl(Type t, String i, boolean b)
    {
        ty = t;
        id = i;
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
        if (choice == 0)
            ret = ty.toString(t) + id;
        else
            ret = ty.toString(t) + id + "[]";
        return ret;
    }

    public void typeCheck(VarTable table, int scope) throws ExampleException
    {
	boolean res = table.add(id,ty.toString(0),scope);
	if (!res)
	    throw new ExampleException("Error: " + id + " can't be redeclared");
    }
}
