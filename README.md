# Assignment03
I refactored most of the original file to follow an MVC model because the GUI was very crowded. Because of this I am unable to reference line by line of what I moved or changed. Overall, I moved the GUI into the NotePadView class, the action events into the NotePadController class, and the database into the NotePadModel class. The controller acted as the middle man between the view and model, often times updating both when necessary.

The following were also refactored due to bad code smells:
1. fields were originally not encapsulated
2. field names were origianlly not self-explanatory
3. undo() method was deadcode as command was empty and not required 
4. the whole programm was jammed into one class and lacked structure and readability
