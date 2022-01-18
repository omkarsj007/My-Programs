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
}
