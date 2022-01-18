class BinaryOp implements Token
{
  Expr lhs, rhs;
  String op;
  public BinaryOp(Expr l, String o, Expr r)
  {
    lhs = l;
    op = o;
    rhs = r;
  }

  public String toString(int t)
  {
    return lhs.toString(t) + " " + op + " " + rhs.toString(t);
  }

    public String typeCheck(VarTable table, int scope) throws ExampleException
    {
	String l,r;
	l = lhs.typeCheck(table, scope);
	r = rhs.typeCheck(table, scope);
	
	if((l.equals("str") || r.equals("str")) && op.equals("+"))
	    {
		return "str";
	    }
		    
	else if ((l.equals("float") || l.equals("int")) && (r.equals("int") || r.equals("float")))
	    {
		if(l.equals("int") && r.equals("int"))
		    {
			return "int";
		    }
		return "float";
	    }
	else
	    {
		throw new ExampleException("Error: Illegal binary operation");
	    }
    }
}
