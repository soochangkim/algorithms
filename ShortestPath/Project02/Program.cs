using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace Project02
{
    class Program
    {
        static int[,] allPath;
        static int[,] requestList;
        const int MAXPATH = 8;

        static void Main(string[] args)
        {
            string[,] shortestPathRoot = new string[MAXPATH, MAXPATH];
            int[,] shortestPathWeigh = new int[MAXPATH, MAXPATH];
            bool[,] isFound = new bool[MAXPATH, MAXPATH];
            string inputData = @"..\..\project2SampleInputData.txt";
            string requestData = @"..\..\project2SampleRequestData.txt";
            //string outputData = @"";
            string option = "";
            int[] temp = new int[3];
            if (checkFilePath(inputData))
            {
                allPath = makePathArray(File.ReadAllLines(inputData), 1);
            }

            if (checkFilePath(requestData))
            {
                requestList = makePathArray(File.ReadAllLines(requestData), 2);
            }

            showMenu();
            option = Console.ReadLine();
            Console.Clear();
            switch (option)
            {
                case "1":
                    initData(shortestPathWeigh, shortestPathRoot, isFound);
                    findSortestPath(shortestPathWeigh, shortestPathRoot, isFound);
                    showShortestPath(shortestPathWeigh, shortestPathRoot);
                    break;
                case "2":
                    temp = findBestBudget();
                    Console.WriteLine(changeIntegerToChar(temp[0]) + " -> " + changeIntegerToChar(temp[1]) + "\tTotal Weight:\t" +temp[2]);
                    break;
                default:
                    Console.WriteLine("You choose wrong option");
                    break;
            }

        }

        public static bool checkFilePath(string filePath)
        {
            if (!File.Exists(filePath))
            {
                Console.WriteLine("Cannot find the "+ filePath.ToString()+ ".\nPlease check the file name again");
                Environment.Exit(0);
            }
            return true;
        }

        public static void findSortestPath(int[,] shortestPathWeigh, string[,] shortestPathRoot, bool[,] isFound)
        {
            for (int i = 0; i < MAXPATH; i++)
            {
                shortestPath(i, shortestPathWeigh, shortestPathRoot, isFound);
            }
            changeUndefined(shortestPathWeigh, shortestPathRoot);
        }

        public static void changeUndefined(int[,] shortestPathWeigh, string[,] shortestPathRoot)
        {
            for (int i = 0; i < MAXPATH; i++)
            {
                for (int j = 0; j < MAXPATH; j++)
                {
                    if (shortestPathWeigh[i, j] == int.MaxValue / 10000)
                    {
                        shortestPathRoot[i, j] = "X";
                        shortestPathWeigh[i, j] = -1;
                    }
                }
            }
        }

        public static void initData(int[,] shortestPathWeigh, string[,] shortestPathRoot, bool[,] isFound)
        {
            for (int i = 0; i < MAXPATH; i++)
            {
                for (int j = 0; j < MAXPATH; j++)
                {
                    shortestPathWeigh[i, j] = (0 == allPath[i, j]) ? int.MaxValue / 10000 : allPath[i, j];
                    shortestPathRoot[i, j] = (0 == shortestPathWeigh[i,j]) ? ""  : (changeIntegerToChar (i)+ " -> " + changeIntegerToChar(j));
                    isFound[i, j] = false;
                    //Console.Write(shortestPathWeigh[i, j] +"\t");
                }
                //Console.WriteLine();
            }
        }

        static void shortestPath(int s, int[,] shortestPathWeigh, string[,] shortestPathRoot, bool[,] isFound)
        {
            isFound[s,s] = true;
            shortestPathWeigh[s, s] = 0;
            shortestPathRoot[s, s] = changeIntegerToChar(s);

            for (int i = 0; i < MAXPATH; i++)
            {
                int u = findNext(s, shortestPathWeigh, isFound);
                
                if (u == -1) return;
                isFound[s, u] = true;
                
                for (int j = 0; j < MAXPATH; j++)
                {
                    if (!isFound[s, j])
                    {
                        if (shortestPathWeigh[s, j] > shortestPathWeigh[s, u] + shortestPathWeigh[u, j])
                        {
                            shortestPathWeigh[s, j] = shortestPathWeigh[s, u] + shortestPathWeigh[u, j];
                            shortestPathRoot[s, j] = shortestPathRoot[s, u] + " -> " + changeIntegerToChar(j);
                        }
                    }
                }
            }
        }

        public static int findNext(int idx, int[,] shortestPathWeigh, bool[,] isFound)
        {
            int temp = -1;
            int min = int.MaxValue;
            for(int i = 0; i < MAXPATH; i++)
            {
                if (!isFound[idx, i] && min > shortestPathWeigh[idx, i])
                {
                    min = shortestPathWeigh[idx, i];
                    temp = i;
                }
            }
            return temp;
        }

        public static int[,] makePathArray(string[] str, int opt)
        {
            int[,] paths = new int[MAXPATH, MAXPATH]; ;

            foreach (string s in str)
            {
                if ('#' == s[0]) continue;
                string[] temp = s.Split('\t');

                //Console.WriteLine(s);//Show All Inputs
                paths[int.Parse(temp[0]) - 1, int.Parse(temp[1]) -1] = int.Parse(temp[2]);
                //paths[int.Parse(temp[1]) - 1, int.Parse(temp[0]) - 1] = int.Parse(temp[2]);
            }
            return paths;
        }

        public static void showMenu()
        {
            Console.WriteLine("option");
            Console.WriteLine("1. Show the shortest path");
            Console.WriteLine("2. best node to shorten the path");
        }

        public static void showShortestPath(int[,] shortestPathWeigh, string[,] shortestPathRoot)
        {
            Console.WriteLine("Your Request\tShortest Path\tTotal Weight");
            for(int i = 0; i < requestList.GetUpperBound(0); i++)
            {
                for(int j = 0; j < requestList.GetUpperBound(1); j++)
                { 
                    if(requestList[i, j] != 0)
                    {
                        Console.Write(changeIntegerToChar(i) + " - > " + changeIntegerToChar(j));
                        Console.Write("\t\t" + shortestPathRoot[i, j]);
                        Console.Write("\t" + shortestPathWeigh[i,j] + "\n");
                    }
                }
            }
        }

        public static string changeIntegerToChar(int i)
        {
            return ((char)('A' + i)).ToString();
        }

        public static int[] findBestBudget()
        {
            int[,] tempWeigh = new int[MAXPATH, MAXPATH];
            bool[,] tempIsFound = new bool[MAXPATH, MAXPATH];
            string[,] tempRoot = new string[MAXPATH, MAXPATH];
            int[] bestNode = new int[3];

            bestNode[2] = int.MaxValue;
            for(int i = 0; i < MAXPATH; i++)// MAXPATH
            {
                for(int j = 0; j < MAXPATH; j++)
                {
                    if(allPath[i,j] != 0)
                    {
                        initData(tempWeigh, tempRoot, tempIsFound);
                        tempWeigh[i, j] /= 2;
                        findSortestPath(tempWeigh, tempRoot, tempIsFound);

                        int temp = sumAllPath(tempWeigh);
                        
                        if (bestNode[2] > temp)
                        {
                            bestNode[0] = i;
                            bestNode[1] = j;
                            bestNode[2] = temp;
                        }
                    }
                }
            }

            return bestNode;
        }

        public static int sumAllPath(int[,] ar1)
        {
            int temp = 0;
            for(int i = 0; i < MAXPATH; i++)
            {
                for(int j = 0; j < MAXPATH; j++)
                {
                    if(ar1[i,j] != -1)
                    {
                        temp += ar1[i, j];
                    }
                }
            }
            return temp;
        }
        



    }
}
