/**
 *  Times, feito por Samuel Deboni Fraga
 *  "Gambiarra total"
 * */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<stdbool.h>


// a global string to recive the tmp file contents
typedef struct {
    char* name;
    char* nick;
    char* ground;
    char* coach;
    char* filename;
    char* league;
    int capacity;
    int foundDay;
    int foundMonth;
    int foundYear;
    long tamPage;
} time;

/*
 * Initialize a new time
 * */
time* new_time() {
    time* time_ptr = (time*)malloc(sizeof(time));

    if (time_ptr != NULL) 
    {
        time_ptr->name = (char*)calloc(512, sizeof(char));
        time_ptr->nick = (char*)calloc(512, sizeof(char));
        time_ptr->ground = (char*)calloc(512, sizeof(char));
        time_ptr->coach = (char*)calloc(512, sizeof(char));
        time_ptr->filename = (char*)calloc(512, sizeof(char));
        time_ptr->league = (char*)calloc(512, sizeof(char));
        time_ptr->capacity = 0;
        time_ptr->foundDay = 0;
        time_ptr->foundMonth = 0;
        time_ptr->foundYear = 0;
        time_ptr->tamPage = 0;
    }
    return time_ptr;
}

/**
 * Free the structure team from memory
 * */
void free_time(time* t)
{
    if (t != NULL)
    {
        free(t->name);
        free(t->nick);
        free(t->ground);
        free(t->coach);
        free(t->filename);
        free(t->league);
        free(t);
    }
}

/**
 * Prints on screen the time atributies
*/
void show_time(time* t) {
    printf("%s ## ", t->name);
    printf("%s ## ", t->nick);
    printf("%i/%i/%i ## ", t->foundDay, t->foundMonth, t->foundYear);
    printf("%s ## ", t->ground);
    printf("%i ## ", t->capacity);
    printf("%s ## ", t->coach);
    printf("%s ## ", t->league);
    printf("%s ## ", t->filename);
    printf("%i ##\n",t->tamPage);
    
}

/**
 * Remove the html tags from the string s2 and returns using the string s3
*/
void remove_tags(char* s2, char* s3) {
    int i;
    int j = 0;
    int size = strlen(s2);
    int count = 0;
    int count2 = 0;
    for(i = 0; i < size; i++) {
        if (s2[i] == '<') 
            count = 1;
        else if(s2[i] == '>') 
        {
            count = 0;
            if(i > 0 && j > 0 && s3[j-1] != '\n' && s2[i-1] != 'i' && s2[i-1] != '/' && s3[j-1]) 
            {
                s3[j] = '\n';
                j++;
            }
        }
        else if(count == 0 && s2[i] == '&')
            count2 = 1;
        else if(count == 0 && s2[i] == ';')
            count2 = 0;
        else if(count == 0 && count2 == 0) 
        {
            s3[j] = s2[i];
            j++;
        }
    }
}

/**
 * Find in the important some the information given by the key 
 * return using the result string
 * s is the input string
 * len is the result string length
*/
void find_line(char* s, char* key, char* result, int len)
{
    char tmp[len];
    int size = strlen(s);
    int offset = 0;

    while (strcmp(tmp, key) != 0 && offset < size)
    {
        //printf("key = %s; tmp = %s\n", key, tmp);
        int i;
        for (i = 0; i < len && s[i+offset] != '\n'; i++) {
            tmp[i] = s[i+offset];
        }
        tmp[i] = '\0';
        i++;
        offset += i;
    }
    //printf("yay ", tmp);
    int i;
    for (i = 0; i < len && s[i+offset] != '\n'; i++) {
        tmp[i] = s[i+offset]; 
    }
    tmp[i] = '\0';
    strcpy(result,tmp);
}

/**
 * Read the team capacity from the string s3 and stores on the capacity variable
*/
void read_capacity(char* s3, char* tmp, int tmp_len, time* time_ptr) {
    char* tmp2 = (char*) malloc(tmp_len * sizeof(char));
    find_line(s3, "Capacity",tmp, tmp_len);

    int j = 0;
    for (int i = 0; i < tmp_len; i++) {
        if(tmp[i] != ',')
            tmp2[j++] = tmp[i];
    }
    
    sscanf(tmp2,"%d", &(time_ptr->capacity));
}

/**
 * Read the date from the string s3 and puts on the time->foundYear
*/
void read_date(char* s3, char* tmp, int tmp_len, time* time_ptr) {
    find_line(s3, "Founded",tmp, tmp_len);
    //printf("year %s\n",tmp);
    if (strlen(tmp) == 4)
        sscanf(tmp,"%d",&(time_ptr->foundYear));
}


/**
 * parses the html looking for the teams information and stores on the time_ptr struct
*/
int parse_html(char* filename, time *time_ptr) {
    
    strcpy(time_ptr->filename,filename);
    FILE* fn = fopen(filename,"r");
    
    if (fn) {

        // temp variables
        char* s = (char*) calloc(256, sizeof(char));
        char* tmp = (char*) calloc(512, sizeof(char));
        char* s2 = (char*) calloc(10000, sizeof(char));
        char* s3 = (char*) calloc(5000, sizeof(char));

        s = fgets(s, 49, fn);

        // search strings
        char* comp = "<table class=\"infobox vcard\" style=\"width:22em\">";
        char* comp2 = "put\"><table class=\"infobox vcard\" style=\"width:2";

        while (strcmp(s, comp) != 0) 
        {
            if(strcmp("</html>\n",s) == 0)
                return 1;
            
            // if the second search is found breaks from the loop
            if(strcmp(s,comp2) == 0)
                break;

            s = fgets(s, 49, fn);
        }

        // read the rest of the line
        s2 = fgets(s2, 10000, fn);

        remove_tags(s2,s3);
        
        int tmp_len = 512;

        // read the name
        find_line(s3, "Full name",tmp, tmp_len);
        strcpy(time_ptr->name,tmp);
        
        // read the nickname
        find_line(s3, "Nickname(s)",tmp, tmp_len);
        strcpy(time_ptr->nick,tmp); 

        // read the ground
        find_line(s3, "Ground",tmp, tmp_len);
        strcpy(time_ptr->ground,tmp);

        // read the coach
        find_line(s3, "Manager",tmp, tmp_len);
        strcpy(time_ptr->coach,tmp);

        // in the case that the first key was not found
        if (strcmp(time_ptr->coach,"") == 0)
        {
            find_line(s3, "Head Coach",tmp, tmp_len);
            strcpy(time_ptr->coach,tmp);
        }

        // read the league
        find_line(s3, "League",tmp, tmp_len);
        strcpy(time_ptr->league,tmp);

        read_capacity(s3, tmp, tmp_len, time_ptr);

        // reads the file size
        fseek(fn,0,SEEK_END);
        time_ptr->tamPage = ftell(fn);
        
        read_date(s3,tmp,tmp_len,time_ptr);
        fclose(fn);
    }
    else
    {
        printf("File not found\n");
        return 1;
    }
    
    return 0;
}

int main () {
    char* path = (char*)calloc(256, sizeof(char));
    scanf("%s",path);
    while(path != NULL && strcmp(path,"FIM") != 0)
    {
        time* time_tmp = new_time();
        parse_html(path, time_tmp);
        show_time(time_tmp);
        
        free_time(time_tmp);
        scanf("%s",path);
    }

    return 0;
}
