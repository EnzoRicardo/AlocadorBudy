public class Main {
    public static void main(String[] args) {

        int totalMemory = 4 * 1024 * 1024;

        BudyAllocator allocator = new BudyAllocator(totalMemory);

        InputReader reader = new InputReader();
        ProgramEntry[] programs = reader.readFile("programas.txt");


        for (int i = 0; i < programs.length; i++) {
            ProgramEntry p = programs[i];
            allocator.allocate(p.label, p.sizeKB * 1024);
        }

        allocator.printReport();
    }
}
