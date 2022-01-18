class Optionalsemi implements Token
{
    int choice;

    public Optionalsemi(boolean b)
    {
        choice = 0;
    }

    public Optionalsemi()
    {
        choice = 1;
    }

    public String toString(int t)
    {
        String ret = "";
        if (choice == 0)
            ret = ";";
       
        return ret;
    }
}
