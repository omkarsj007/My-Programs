class BooleanOp implements Token
{
    Expr lhs, rhs;
    String op;
    public BooleanOp(Expr l, String o, Expr r)
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
	if(op.equals("&&") || op.equals("||"))
            {
                if(l.equals("bool") && r.equals("bool"))
                    {
                        return "bool";
                    }
                else
                    {    throw new ExampleException("Error: logical operand must have bool on both sides");
                    }
            }

	if((l.contains("int") ||l.equals("float")) && (r.contains("int") || r.equals("float")))
	    {
		return "bool";
	    }
	else
	    {
		throw new ExampleException("Error: logical operands must have float or int on both sides");
	    }
       
    }
	
}
