class Methoddecl implements Token
{
    String r;
    String id;
    Argdecls as;
    Fielddecls fs;
    StmtList st;
    Optionalsemi op;
    Type type;
    int choice;

    public Methoddecl(String rd, String i, Argdecls a, Fielddecls f, StmtList s, Optionalsemi o)
    {
        r = rd;
	id = i;
	as = a;
	fs = f;
	st = s;
	op = o;
	choice = 0;
    }

    public Methoddecl(Type ty, String i, Argdecls a, Fielddecls f, StmtList s, Optionalsemi o)
    {
        type = ty;
	id = i;
	as = a;
	fs = f;
	st = s;
	op = o;
	choice = 1;
    }

    

    public String toString(int t)
    {
        String ret = "";
	if(choice == 0)
        ret = r + " " + id + "( "+ as.toString(t)+" ){\n" + fs.toString(t) + st.toString(t)+ "\n}" + op.toString(t) + "\n";
	else
	    ret = type.toString(t) + " " + id + "( "+ as.toString(t)+" ){\n" + fs.toString(t) + st.toString(t)+ "\n}" + op.toString(t) + "\n";
	return ret;
    }
    
    public void typeCheck(VarTable table, int scope) throws ExampleException
    {
	if(choice == 0)
	{
	    String thisType = table.getType(id,scope-1);
	    if(!thisType.equals(""))
		{
		    throw new ExampleException("Error: " + id + " can't be redeclared");
		}

	    boolean res = table.add(id,r, scope-1);
	if (!res)
	    throw new ExampleException("Error: " + id + " can't be redeclared");
	
	as.typeCheck(table, scope);
	fs.typeCheck(table, scope);
	boolean hasReturn = st.typeCheck(table, scope, "void");

	if(hasReturn == true)
	    {
		throw new ExampleException("Error: function has no return type");
	    }
	table.removeScope(scope);
	}


	else
	{
	    String thisType = table.getType(id,scope-1);
	    if(!thisType.equals(""))
		{
		    throw new ExampleException("Error: " + id + " can't be redeclared");
		}

	    boolean res = table.add(id,type.toString(0), scope-1);
	if (!res)
	    throw new ExampleException("Error: " + id + " can't be redeclared");

	as.typeCheck(table, scope);
	fs.typeCheck(table, scope);
	boolean hasReturn = st.typeCheck(table, scope, type.typeCheck(table,scope));

	if(hasReturn == false)
	    {
		throw new ExampleException("Error: function is missing a return statement");
	    }
	table.removeScope(scope);

	}
    
    }
}
