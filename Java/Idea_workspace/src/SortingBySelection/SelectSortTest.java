package SortingBySelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SelectSortTest {

    private static final boolean AUTOMATED = true;
    private static class SortObject implements Comparable<SortObject> {

        public int getRating() {
            return rating;
        }

        // makes sure that the rating is higher then the other one
        public void increaseRating(SortObject other) {
            if (rating < other.getRating()) {
                rating = other.getRating() + 1;
//                other.rating++;
            } else {
                rating++;
                other.rating++;
            }
        }

        private int rating;
        public int value;

        public SortObject(int value) {
            this.value = value;
            this.rating = 0;
        }

        /**
         * return 0 if rating is the same
         * returns -1 if the rating of o is lower
         * returns 1 if the rating of o is higher
         */
        @Override
        public int compareTo(SortObject o) {
            if (o.rating == rating) return 0;
            if (o.rating < rating) return -1;
            return 1;
        }
    }

    public static void main(String[] args) {

        List<SortObject> unsorted = new ArrayList<>();

        List<SortObject> sorted = new ArrayList<>();

        int anzahl = 1000;
        for (int i = 0; i < anzahl; i++) {
            unsorted.add(new SortObject((int) (Math.random() * anzahl) + 1));
        }

        Scanner console = new Scanner(System.in);

        int cnt = 0;
        while (true) {

            for (SortObject o : sorted
                    ) {
                System.out.print(o.value + ", ");
            }
            System.out.println();

            if (unsorted.size() == 0) {
                System.out.println("Runs = " + cnt);
                break;
            }


            SortObject toInsert = unsorted.get(0);
            unsorted.remove(0);

            if (sorted.size() == 0) {
                sorted.add(toInsert);
                continue;
            }

            int start = 0;
            int end = sorted.size() - 1;
            int mid = (start + end) / 2 + (start + end) % 2;

            int singleInsertCnt = 0;

            while (end >= start) {
                singleInsertCnt++;
                System.out.println("----------");
                System.out.println("indizes = " + start + " - " + mid + " - " + end);
                System.out.println("einzufügen = " + toInsert.value + " (" + toInsert.getRating() + ")");
                System.out.println("zu vergleichender eintrag = " + sorted.get(mid).value + " (" + sorted.get(mid).getRating() + ")");

                System.out.println("Choose the better one:");
                System.out.println("press 1 to choose the first and 2 to choose the second, choose 0 if equal");
                System.out.println(sorted.get(mid).value + " - " + toInsert.value);
                if(!AUTOMATED) {
                    if (console.hasNextInt()) {
                        int next = console.nextInt();
                        if (next == 1) {
                            end = mid - 1;
                            mid = (start + end) / 2;

                            //          break;
                        } else if (next == 2) {
                            start = mid + 1;
                            mid = (start + end) / 2;
                        } else if (next == 0) {
                            end--;
                            mid = (start + end) / 2;
                        }
                    }
                    // testing
                }else {
                    if (sorted.get(mid).value > toInsert.value) {
                        end = mid - 1;
                        mid = (start + end) / 2;

                        //          break;
                    } else if (sorted.get(mid).value < toInsert.value) {
                        start = mid + 1;
                        mid = (start + end) / 2;
                    } else if (sorted.get(mid).value == toInsert.value) {
                        end--;
                        mid = (start + end) / 2;
                    }
                }


                ++cnt;
//                unsorted.sort(new Comparator<SortObject>() {
//                    @Override
//                    public int compare(SortObject o1, SortObject o2) {
//                        return o1.compareTo(o2);
//                    }
//                });
                System.out.println("run number = " + cnt);
            }

            System.out.println(start + " - " + mid + " - " + end);
            System.out.println("took " + singleInsertCnt + " runs to insert a single value");
            sorted.add(start, toInsert);

        }


    }

}
