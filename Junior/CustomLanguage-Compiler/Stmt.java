class Stmt implements Token
{
    Expr e;
    Stmt s;
    OptionalAsn asn;
    String id;
    String op;
    Name n;
    Readlist r;
    Printlist p;
    Printlinelist pl;
    IfEnd end;
    Args a;
    Fielddecls f;
    StmtList sl;
    Optionalsemi opsemi;
    int choice;

    public Stmt(Expr ed, Stmt sd, IfEnd endd)
    {
	e = ed;
	s = sd;
  end = endd;
	choice = 0;
    }
    
    public Stmt(Expr ed, Stmt sd)
    {
	e = ed;
	s = sd;
	choice = 1;
    }
    
    public Stmt(Name nd, Expr ed)
    {
	e = ed;
	n = nd;
	choice = 2;
    }

    public Stmt(Readlist rd)
    {
	r = rd;
	choice = 3;
    }

    public Stmt(Printlist pd)
    {
	p = pd;
	choice = 4;
    }
    
    public Stmt(Printlinelist pld)
    {
	pl = pld;
	choice = 5;
    }

    public Stmt(String i)
    {
	id = i;
	choice = 6;
    }

    public Stmt(String i, Args ad)
    {
	id = i;
	a = ad;
	choice = 7;
    }
    
    public Stmt()
    {
	choice = 8;
    }
    
    public Stmt(Expr ed)
    {
	e = ed;
	choice = 9;
    }

    public Stmt(Name nd, String opd)
    {
	n = nd;
	op = opd;
	choice = 10;
    }

    public Stmt(Fielddecls fds, StmtList l, Optionalsemi s)
    {
	f = fds;
	sl = l;
	opsemi = s;
	choice = 11;
    }
	

  public String toString(int t)
  {
      String tabs = "";
    for (int i = 0; i < t; ++i)
      tabs += "\t";
    
    String ret = "";
    if(choice == 0)
    {
      ret = tabs + "if ( " + e.toString(t) + " ) \n" + s.toString(t+1) + end.toString(t+1) + "\n";
    }
    else if(choice == 1)
    {
      ret = tabs + "while ( " + e.toString(t) + " ) \n" + s.toString(t+1) + "\n";
    }
    else if(choice == 2)
    {
      ret = tabs + n.toString(t) + " = " + e.toString(t) + ";\n";
    }
    else if(choice == 3)
    {
      ret = tabs + "read ( " + r.toString(t) + " ); " + "\n";
    }
    else if(choice == 4)
    {
      ret = tabs + "print ( " + p.toString(t) + " ); " + "\n";
    }
    else if(choice == 5)
    {
      ret = tabs + "printline ( " + pl.toString(t) + " ); " +  "\n";
    }
    else if(choice == 6)
    {
      ret = tabs + id + "(); " + "\n";
    }
    else if(choice == 7)
    {
      ret = tabs + id + " ( " + a.toString(t) + " ); " + "\n";
    }
    else if(choice == 8)
    {
      ret = tabs + "return; " + "\n";
    }
    else if(choice == 9)
    {
      ret = tabs + "return " + e.toString(t) + ";\n";
    }
    else if(choice == 10)
    {
      ret = tabs + n.toString(t) + op +  ";\n";
    }
    else if(choice == 11)
    {
	ret = tabs + "{ " + f.toString(t+1) + sl.toString(t+1) + tabs +  " }" + opsemi.toString(t) + "\n";
    }
    return ret;
  }

    public boolean typeCheck(VarTable table, int scope, String type) throws ExampleException
    {
	boolean hasReturn = false;

	if (choice == 0)
	{
	String thisType = e.typeCheck(table, scope);
        if (thisType.equals(""))
	    throw new ExampleException("Error: " + id + " not declared");
	if (thisType.equals("bool") || thisType.equals("int"))
	    {
	    }
	else
	    throw new ExampleException("Error: if condition must be bool");
	
	hasReturn = s.typeCheck(table, scope, type);
	hasReturn = (hasReturn ||  end.typeCheck(table, scope, type));
	}

	if (choice == 1)
	{
	String thisType = e.typeCheck(table, scope);
        if (thisType.equals(""))
	    throw new ExampleException("Error: " + id + " not declared");
	if (thisType.equals("bool") || thisType.equals("int"))
	    {
	    }
	else
	    throw new ExampleException("Error: if condition must be bool");
	
	return s.typeCheck(table, scope, type);
	
	}

	if (choice == 2)
	{
	    String thisType = n.typeCheck(table,scope);
	    if (thisType.equals(""))
	    throw new ExampleException("Error: " + id + " not declared");

	    String thisType2 = e.typeCheck(table, scope);
	    if(thisType.equals(thisType2));
	       else if(thisType.equals("float") && thisType2.equals("int"));
	       else if (thisType.equals("bool") && thisType2.equals("int"));
	       else if (thisType.contains("final"))
		   throw new ExampleException("Error: cannot change the value of a constant/final");
	       else{      
			    throw new ExampleException("Error: Illegal Assignment operation");
			}
	}
	
	if (choice == 3)
	    {
		r.typeCheck(table, scope);
	    }

	if (choice == 4)
	    {
		p.typeCheck(table, scope);
	    }
	
	if (choice == 5)
	    {
		//pl.typeCheck(table, scope);
	    }
	
	if (choice == 6 || choice == 7)
	    {
		String thisType = table.getType(id, scope);
		if (thisType.equals(""))
		    throw new ExampleException("Error: " + id + " not declared");
	    }
	
	if (choice == 8)
	    {
		if(type.equals("void"))
		    {
		     
		    }
		else
		    {
			throw new ExampleException("Error: return type is not void");
		    }
	    }
	
	if (choice == 9)
	    {
		String thisType = e.typeCheck(table, scope);
		if(type.equals(thisType))
		    {
			hasReturn = true;
		    }
		else
		    {
			throw new ExampleException("Error: incorrect type returned");
		    }
	    }

	if (choice == 10)
	    {
		String thisType = n.typeCheck(table, scope);
		if(thisType.contains("final"))
		    throw new ExampleException("Error: cannot increment/decrement final");

		if(thisType.equals("int") || thisType.equals("float"))
		    {
		    }
		else
		    throw new ExampleException("Error: variable other than int or float cannot be incremented or decremented");
	    }
	
	if (choice == 11)
	    {
		f.typeCheck(table, scope+1);
		hasReturn = sl.typeCheck(table, scope+1, type);
	    }
	return hasReturn;
    }
}
	       
	
	       
