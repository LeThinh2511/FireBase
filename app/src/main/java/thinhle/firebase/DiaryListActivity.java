package thinhle.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.attr.category;

public class DiaryListActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference journalCloudEndPoint;
    private DatabaseReference tagCloudEndPoint;
    private Button btnAdd;
    private ListView lv;
    private ArrayAdapter<String> arrayAdapter;

    private DiaryListActivity SampleData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv_content);
        btnAdd = (Button) findViewById(R.id.btn_add);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        journalCloudEndPoint = mDatabase.child("journalentris");
        tagCloudEndPoint = mDatabase.child("tags");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInitialDataToFirebase();
            }
        });




        final ArrayList<String> mJournalEntries = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mJournalEntries);

        journalCloudEndPoint.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                JournalEntry journalEntry = dataSnapshot.getValue(JournalEntry.class);
                mJournalEntries.add(journalEntry.getTitle() + "\n" + journalEntry.getContent());
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lv.setAdapter(arrayAdapter);

    }
    public static List<JournalEntry> getSampleJournalEntries()
    {
        //tao mot list gom cac journalEntries

        List<JournalEntry> journalEntries = new ArrayList<>();
        //create the dummy journal
        JournalEntry journalEntry1 = new JournalEntry();

        journalEntry1.setTitle("DisneyLand Trip");
        journalEntry1.setContent("We went to Disneyland today and the kids had lots of fun!");

        Calendar calendar1 = Calendar.getInstance();
        journalEntry1.setDateModified(calendar1.getTimeInMillis());

        journalEntries.add(journalEntry1);

        JournalEntry journalEntry2 = new JournalEntry();

        journalEntry2.setTitle("Da Nang");
        journalEntry2.setContent("We went to Da Nang today and the kids had lots of fun!");

        Calendar calendar2 = Calendar.getInstance();
        journalEntry2.setDateModified(calendar2.getTimeInMillis());

        journalEntries.add(journalEntry2);

        return journalEntries;
    }

    public static List<String> getSampleTags()
    {

        List<String> tags = new ArrayList<>();
        //create the dummy journal
        String tag1 = "";
        String tag2 = "";

        tags.add(tag1);
        tags.add(tag2);

        return tags;
    }

    private void addInitialDataToFirebase() {

        List<JournalEntry> sampleJournalEntries = SampleData.getSampleJournalEntries();

        for (JournalEntry journalEntry: sampleJournalEntries){
            String key = journalCloudEndPoint.push().getKey();
            journalEntry.setJournalId(key);
            journalCloudEndPoint.child(key).setValue(journalEntry);
        }

        List<String> tagNames = SampleData.getSampleTags();

        for (String name: tagNames){
            String tagKey = tagCloudEndPoint.push().getKey();
            Tag tag = new Tag();
            tag.setTagName(name);
            tag.setTagId(tagKey);
            tagCloudEndPoint.child(tag.getTagId()).setValue(category);
        }

    }
}
