public class BudyAllocator {
    private int totalMemory;
    private int[] blockStatus;
    private int[] blockSize;
    private int[] blockStart;
    private int blockCount;

    public BudyAllocator(int total) {
        totalMemory = total;

        blockStatus = new int[1000];
        blockSize = new int[1000];
        blockStart = new int[1000];

        blockCount = 1;

        blockStatus[0] = -1;
        blockSize[0] = totalMemory;
        blockStart[0] = 0;
    }

    public void allocate(char label, int sizeBytes){
        int target = 1;

        while(target < sizeBytes) {
            target = target * 2;
        }

        int pos = -1;

        for (int i =0; i < blockCount; i++){
            if (blockStatus[i] == -1 && blockSize[i] >= target){
                pos = i;
                break;
            }
        }
        if (pos == -1){
            System.out.println("Não foi possivel alocar o programa " + label);
            return;
        }
        while (blockSize[pos] > target){
            splitBudy(pos);
        }

        blockStatus[pos] = label;
    }

    public void free(char label) {
        for (int i = 0; i < blockCount; i++){
            if (blockStatus[i] == label){
                blockStatus[i] = -1;
            }
        }

        mergeBuddies();
    }

    public void printReport() {
        int totalLivre = 0;
        int fragmentos = 0;

        for (int i = 0; i < blockCount; i++){
            if (blockStatus[i] == -1){
                totalLivre = totalLivre + blockSize[i];
                fragmentos = fragmentos + 1;
            }
        }

        System.out.println("==== Relatorio da Memoria ====");
        System.out.println("Memoria Livre Total: " + totalLivre);
        System.out.println("Fragmentos Livres: " + fragmentos);

        System.out.println("-- Blocos Livres --");
        for (int i = 0; i < blockCount; i++){
            if (blockStatus[i] == -1){
                System.out.println("Pos: " + blockStart[i] + " | Tam: " + blockSize[i]);
            }
        }

        System.out.println("-- Blocos Alocados --");
        for (int i = 0; i < blockCount; i++){
            if (blockStatus[i] != -1){
                char label = (char) blockStatus[i];
                System.out.println("Prog:" + label + " | Pos: " + blockStart[i] + " | Tam:" + blockSize[i]);
            }
        }
    }

    private int splitBudy(int idx){

        int oldSize = blockSize[idx];
        int newSize = oldSize / 2;
        int oldStart = blockStart[idx];

        blockSize[idx] = newSize;
        blockStatus[idx] = -1;
        blockStart[idx] = oldStart;

        int newIndex = blockCount;
        blockCount = blockCount + 1;

        blockSize[newIndex] = newSize;
        blockStatus[newIndex] = -1;
        blockStart[newIndex] = oldStart + newSize;


        return newIndex;
    }

    private void mergeBuddies(){
        boolean merged = true;

        while(merged){
            merged = false;

            for (int i = 0; i < blockCount; i++){
                if (blockStatus[i] != -1){
                    continue;
                }

                for (int j = i + 1; j < blockCount; j++){
                    if (blockStatus[j] != -1){
                        continue;
                    }

                    if (blockSize[i] != blockSize[j]){
                        continue;
                    }

                    int size = blockSize[i];
                    int startA = blockStart[i];
                    int startB = blockStart[j];

                    int low = startA;
                    int high = startB;

                    if (startB < startA){
                        low = startB;
                        high = startA;
                    }

                    if (high - low == size){
                        blockStart[i] = low;
                        blockSize[i] = size * 2;
                        blockStatus[i] = -1;

                        int lastIndex = blockCount - 1;

                        if (j != lastIndex){
                            blockStart[j] = blockStart[lastIndex];
                            blockSize[j] = blockSize[lastIndex];
                            blockStatus[j] = blockStatus[lastIndex];
                        }

                        blockCount = blockCount - 1;

                        merged = true;
                        break;
                    }
                }

                if (merged) {
                    break;
                }
            }
        }
    }
}
