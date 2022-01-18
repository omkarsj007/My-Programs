class Fielddecl implements Token
{
    int choice;
    Optionalfinal of;
    Type ty;
    String id;
    OptionalAsn asn;
    int iliteral;
    String key = "";

    public Fielddecl(Boolean b, Type t, String i, OptionalAsn a)
    {
        ty = t;
	id = i;
	asn = a;
        choice = 0;
	if(b)
	    key = "final";
    }

    public Fielddecl(Type t, String i, int l)
    {
        ty = t;
	id = i;
	iliteral = l;
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
        if (choice == 0)
            ret = key + " " + ty.toString(t) + " " + id + asn.toString(t)+ ";\n";
        else
            ret = ty.toString(t) + " " + id + " [ " + iliteral + " ];\n";
        return ret;
    }

    public void typeCheck(VarTable table, int scope) throws ExampleException
    {
	if(choice == 0)
	    {
		String type = ty.typeCheck(table, scope);
		boolean res = table.add(id,type, scope);
		if (!res)
		    throw new ExampleException("Error: " + id + " can't be redeclared");
		if(key.equals("final"))
		   table.setFinal(id, scope);

		String assignment = asn.typeCheck(table, scope);
		if(assignment.equals("noAssignment"))
		   {return;
		   }
		
		if(assignment.equals(type))
		    {return;
		    }
		
		if(type.equals("float") && (assignment.equals("int") || assignment.equals("float")))
		    {return;
		    }

		else
		    throw new ExampleException("Error: Illegal assignment to fielddecl");
	    }

	else
	    {
		String type = ty.toString(0);
		boolean res = table.add(id,type, scope);
		
		if (!res)
		    throw new ExampleException("Error: " + id + " can't be redeclared");
		table.setArr(id,scope);
	    }
    }
		
	
}
