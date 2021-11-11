
package UtilLib;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ObjectLib.Location;

public class DatabaseManager {

    // Singleton DatabaseManager
    private static final DatabaseManager Data = new DatabaseManager();
    public static DatabaseManager getInstance() {return Data;}

    //DATABASE HEADERS

    // ACCOUNTS
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String UNIT = "unitpref";
    public static final String LANDMARK = "landmarkpref";
    public static final String RETURN = "return";

    private String TAG = "DataManger";

    private DatabaseReference locationDB = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(AccountManager.getActiveUser().getUid()).child("Collections");
 //private StorageReference storageRef = FirebaseStorage.getInstance().getReference("Collection Images");

    List<Location> locations = new ArrayList<Location>();

    public DatabaseReference getLocationDB(){
        return locationDB;
    }

    public DatabaseManager(){
        locationDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (locations!=null) {
                    locations.clear();
                }
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    Location loc = snap.getValue(Location.class);
                    loc.setId(snap.getKey());
                    //System.out.println( coll.toString() );
                    if (loc == null){
                        System.out.println( "error reading object collection");
                    }
                    else locations.add(loc);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    //Active Objects//
    private String activeLocation;
    //private String activeItem;
    //private String activeTask;
    //private String activeObjective;

    public Location getActiveLocationData() {
        for (Location loc: locations) {
            if (loc.getId().equals(activeLocation)){
                return loc;
            }
        }
        return null;
    }


    /*
    public void setActiveCollection(ItemCollection ac){

        if (ac != null){
            List<Collectible> in = new ArrayList<Collectible>();
            //System.out.println("Adding active Collections Items for item: " +ac.getId() );
            for (Collectible item:items) {
                if (item.getCollectionId().equals(ac.getId())){
                    //System.out.println(item.toString());
                    in.add(item);
                }
            }
            ac.setCollectibles(in);
            for (ItemCollection coll: collections) {
                if (coll.getId().equals(ac.getId())){
                    //System.out.println(item.toString());
                    activeCollection = coll.getId();
                }
            }
            getActiveCollection().setCollectibles(in);
        }
        else activeCollection = null;

if (activeCollection!=null) {
            System.out.println("Checking active collection for items");
            for (Collectible item : activeCollection.getCollectibles()) {
                //System.out.println(item.toString());
            }
        }

    }
    public void setActiveCollectionFromId(String ac){

        if (ac != null){
            List<Collectible> in = new ArrayList<Collectible>();
            //System.out.println("Adding active Collections Items for item: " +ac.getId() );
            for (Collectible item:items) {
                if (item.getCollectionId().equals(ac)){
                    //System.out.println(item.toString());
                    in.add(item);
                }
            }
            for (ItemCollection coll: collections) {
                if (coll.getId().equals(ac)){
                    //System.out.println(item.toString());
                    activeCollection = coll.getId();
                }
            }
            getActiveCollection().setCollectibles(in);
        }
        else activeCollection = null;

if (activeCollection!=null) {
            System.out.println("Checking active collection for items");
            for (Collectible item : activeCollection.getCollectibles()) {
                //System.out.println(item.toString());
            }
        }

    }
    public void setActiveItem(Collectible inItem){
        if (inItem != null){
            for (Collectible item:items) {
                if (item.getId().equals(inItem.getId())){
                    //System.out.println(item.toString());
                    activeItem = item.getId();
                }
            }
        }
        else activeItem = null;
    }
    public void setActiveTask(Task at){
        if (at != null){
            List<Objective> in = new ArrayList<Objective>();
            //System.out.println("Adding active Collections Items for item: " +ac.getId() );
            for (Objective obj: objectives) {
                if (obj.getTaskId().equals(at.getId())){
                    //System.out.println(item.toString());
                    in.add(obj);
                }
            }
            at.setObjectives(in);
            for (Task task: tasks) {
                if (task.getId().equals(at.getId())){
                    //System.out.println(item.toString());
                    activeTask = task.getId();
                }
            }
            getActiveTask().setObjectives(in);
        }
        else activeTask = null;

if (activeCollection!=null) {
            System.out.println("Checking active collection for items");
            for (Collectible item : activeCollection.getCollectibles()) {
                //System.out.println(item.toString());
            }
        }

    }
    public void setActiveObjective(Objective inObjective){
        if (inObjective != null){
            for (Objective objective:objectives) {
                if (objective.getId().equals(inObjective.getId())){
                    //System.out.println(item.toString());
                    activeObjective = objective.getId();
                }
            }
        }
        else activeObjective = null;
    }

    public void SaveData(){
        //TODO Save Data
    }
    public void LoadData(){
        // TODO Load Data
    }

    public void AddTask(Task task){
        tasks.add(task);
    }
    public void RemoveTask(Task task){
        tasks.remove(task);
    }

    public void AddOrUpdateCollection(ItemCollection collection){
        if (activeCollection == null){
            collection.setId(collectionDatabase.push().getKey());
            collectionDatabase.child(collection.getId()).child("id").setValue(collection.getId());
            collectionDatabase.child(collection.getId()).child("collectionName").setValue(collection.getCollectionName());
            collectionDatabase.child(collection.getId()).child("description").setValue(collection.getDescription());
            collectionDatabase.child(collection.getId()).child("image").setValue(collection.getImage());
            collectionDatabase.child(collection.getId()).child("collectibles").setValue(collection.getCollectibles());
        }
        else{
            collectionDatabase.child(getActiveCollection().getId()).child("id").setValue(collection.getId());
            collectionDatabase.child(getActiveCollection().getId()).child("collectionName").setValue(collection.getCollectionName());
            collectionDatabase.child(getActiveCollection().getId()).child("description").setValue(collection.getDescription());
            collectionDatabase.child(getActiveCollection().getId()).child("image").setValue(collection.getImage());
            //collectionDatabase.child(getActiveCollection().getId()).child("collectibles").setValue(collection.getCollectibles());

        }

    }
    public void AddOrUpdateItem(Collectible in){
        Collectible item = new Collectible();
        item = in;
        if (activeItem == null) {
            String key = itemDatabase.push().getKey();
            System.out.println("New key generated: " + key);
            item.setId(key);
            System.out.println("Adding Item :"+ item.toString());
            itemDatabase.child(item.getId()).child("id").setValue(item.getId());
            itemDatabase.child(item.getId()).child("collectionId").setValue(getActiveCollection().getId());
            itemDatabase.child(item.getId()).child("name").setValue(item.getName());
            itemDatabase.child(item.getId()).child("description").setValue(item.getDescription());
            itemDatabase.child(item.getId()).child("image").setValue(item.getImage());
            itemDatabase.child(item.getId()).child("isFavorite").setValue(item.isFavourite());

            itemDatabase.child(item.getId()).child("acquisitionDate").setValue(item.getAcquisitionDate());
            itemDatabase.child(item.getId()).child("acquisitionLoc").setValue(item.getAcquisitionLoc());

            //itemDatabase.child(item.getId()).child("isLent").setValue(item.isLent());
            itemDatabase.child(item.getId()).child("borrowedTo").setValue(item.getBorrowedTo());
            itemDatabase.child(item.getId()).child("expectedReturn").setValue(item.getExpectedReturn());
        }
        else{
            System.out.println("Updating Item :"+ item.toString());
            itemDatabase.child(getActiveItem().getId()).child("id").setValue(getActiveItem().getId());
            itemDatabase.child(getActiveItem().getId()).child("collectionId").setValue(getActiveCollection().getId());
            itemDatabase.child(getActiveItem().getId()).child("name").setValue(item.getName());
            itemDatabase.child(getActiveItem().getId()).child("description").setValue(item.getDescription());
            itemDatabase.child(getActiveItem().getId()).child("image").setValue(item.getImage());
            itemDatabase.child(getActiveItem().getId()).child("isFavorite").setValue(item.isFavourite());

            itemDatabase.child(getActiveItem().getId()).child("acquisitionDate").setValue(item.getAcquisitionDate());
            itemDatabase.child(getActiveItem().getId()).child("acquisitionLoc").setValue(item.getAcquisitionLoc());

            //itemDatabase.child(getActiveItem().getId()).child("isLent").setValue(item.isLent());
            itemDatabase.child(getActiveItem().getId()).child("borrowedTo").setValue(item.getBorrowedTo());
            itemDatabase.child(getActiveItem().getId()).child("expectedReturn").setValue(item.getExpectedReturn());
        }

    }

    public void AddOrUpdateTask(Task task) {
        if (activeTask == null) {
            task.setId(taskDatabase.push().getKey());
            taskDatabase.child(task.getId()).child("id").setValue(task.getId());
            taskDatabase.child(task.getId()).child("taskName").setValue(task.getTaskName());
            taskDatabase.child(task.getId()).child("date").setValue(task.getDate());
        } else {
            taskDatabase.child(getActiveTask().getId()).child("id").setValue(task.getId());
            taskDatabase.child(getActiveTask().getId()).child("taskName").setValue(task.getTaskName());
            taskDatabase.child(getActiveTask().getId()).child("date").setValue(task.getDate());
            //collectionDatabase.child(getActiveCollection().getId()).child("collectibles").setValue(collection.getCollectibles());
        }
    }
    public void AddOrUpdateObjective(Objective objective) {
        if (activeObjective == null) {
            objective.setId(objectiveDatabase.push().getKey());
            objectiveDatabase.child(objective.getId()).child("id").setValue(objective.getId());
            objectiveDatabase.child(objective.getId()).child("taskId").setValue(getActiveTask().getId());
            objectiveDatabase.child(objective.getId()).child("objectiveName").setValue(objective.getObjectiveName());
            objectiveDatabase.child(objective.getId()).child("description").setValue(objective.getDescription());
            objectiveDatabase.child(objective.getId()).child("isComplete").setValue(objective.isComplete());
        } else {
            objectiveDatabase.child(getActiveObjective().getId()).child("id").setValue(getActiveObjective().getId());
            objectiveDatabase.child(getActiveObjective().getId()).child("taskId").setValue(getActiveTask().getId());
            objectiveDatabase.child(getActiveObjective().getId()).child("objectiveName").setValue(objective.getObjectiveName());
            objectiveDatabase.child(getActiveObjective().getId()).child("description").setValue(objective.getDescription());
            objectiveDatabase.child(getActiveObjective().getId()).child("isComplete").setValue(objective.isComplete());
        }
    }

    public void RefreshActiveCollection(){
        getActiveCollection();
    }
    public void RefreshActiveTask(){
        getActiveTask();
    }

    public void initData(){
        collectionDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(LoginManager.getActiveUser().getUid()).child("Collections");
        itemDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(LoginManager.getActiveUser().getUid()).child("Items");
        taskDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(LoginManager.getActiveUser().getUid()).child("Tasks");
        objectiveDatabase = FirebaseDatabase.getInstance("https://modernpocket-f5780-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users").child(LoginManager.getActiveUser().getUid()).child("Objectives");
    }

    public static String getBitmapAsBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), URL_SAFE) ;
    }
    public static Bitmap getBitmapFromByteArray(byte[] bArray){
        if (bArray != null){
            return BitmapFactory.decodeByteArray(bArray, 0 ,bArray.length);
        }
        return null;
    }
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static Date getDateFromString(String dateIn){

        Date date = new Date();
        try{
            date = dateFormat.parse(dateIn);
        } catch (ParseException e){
            Log.e("DataBase", "getDateFromString: Failed, invalid format ", e );
            return null;
        }
        return date;
    }
    public static boolean validateDateFromString(String dateIn){

        Date date = new Date();
        try{
            date = dateFormat.parse(dateIn);
            return true;
        } catch (ParseException e){
            Log.e("DataBase", "getDateFromString: Failed, invalid format ", e );
            return false;
        }

    }
    public List<Collectible> getFavourites() {
        List<Collectible> out = new ArrayList<Collectible>();
        for (Collectible item : items)
        {
            if (item.isFavourite())
            {
                out.add(item);
            }
        }
        return out;
    }
    public List<Collectible> getLent() {
        List<Collectible> out = new ArrayList<Collectible>();
        for (Collectible item : items)
        {
            if (item.isLent())
            {
                out.add(item);
            }
        }
        return out;
    }

    public void InitCollections(){
        for (int i = 0 ; i < collections.size(); i++) {
            List<Collectible> in = new ArrayList<Collectible>();
            for (Collectible item : items) {
                if (item.getCollectionId().equals(collections.get(i).getId())){
                    //System.out.println(item.toString());
                    in.add(item);
                }
            }
            collections.get(i).setCollectibles(in);
        }
    }
    public void InitTasks(){
        for (int i = 0 ; i < tasks.size(); i++) {
            List<Objective> in = new ArrayList<Objective>();
            for (Objective obj : objectives) {
                if (obj.getTaskId().equals(tasks.get(i).getId())){
                    //System.out.println(item.toString());
                    in.add(obj);
                }
            }
            tasks.get(i).setObjectives(in);
        }
    }

    //Sort
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void SortCollectionsByFilter(String _filter){
        collections.sort(new Comparator<ItemCollection>() {
            @Override
            public int compare(ItemCollection i1, ItemCollection i2) {
                switch (_filter) {
                    case NAME:
                        return i1.getCollectionName().compareTo(i2.getCollectionName());
                    case COMPLEATION:
                        return i2.getCompletion() - i1.getCompletion();
                    default:
                        return 0;
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void SortItemsByFilter(String _filter){
        getActiveCollection().getCollectibles().sort(new Comparator<Collectible>() {
            @Override
            public int compare(Collectible i1, Collectible i2) {
                switch (_filter) {
                    case NAME:
                        return i1.getName().compareTo(i2.getName());
                    case OWNED:
                        return i2.isOwned().compareTo(i1.isOwned());
                    case LENT:
                        return i2.isLent().compareTo(i1.isLent());
                    default:
                        return 0;
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void SortTaskByFilter(String _filter){
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task i1, Task i2) {
                switch (_filter) {
                    case NAME:
                        return i1.getTaskName().compareTo(i2.getTaskName());
                    case COMPLEATION:
                        return i2.getCompletion() - i1.getCompletion();
                    default:
                        return 0;
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void SortObjectivesByFilter(String _filter){
        getActiveTask().getObjectives().sort(new Comparator<Objective>() {
            @Override
            public int compare(Objective i1, Objective i2) {
                switch (_filter) {
                    case NAME:
                        return i1.getObjectiveName().compareTo(i2.getObjectiveName());
                    case OWNED:
                        return i1.isComplete().compareTo(i2.isComplete());
                    default:
                        return 0;
                }
            }
        });
    }
    public void ClearData(){
        tasks.clear();
        collections.clear();
        items.clear();
        objectives.clear();
    }
    public void AttachListenrs(){
        collectionDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (collections!=null) {
                    collections.clear();
                }
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    ItemCollection coll = snap.getValue(ItemCollection.class);
                    coll.setId(snap.getKey());
                    //System.out.println( coll.toString() );
                    if (coll == null){
                        System.out.println( "error reading object collection");
                    }
                    else collections.add(coll);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        itemDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (items!=null) {
                    items.clear();
                }
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    Collectible item = snap.getValue(Collectible.class);
                    item.setId(snap.getKey());
                    try{
                        item.setFavourite(snap.child("isFavorite").getValue(boolean.class));
                    }
                    catch(Exception e){
                        item.setFavourite(false);
                    }
                    if (item == null){
                        System.out.println( "error reading object collection");
                    }
                    else items.add(item);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        taskDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (tasks!=null) {
                    tasks.clear();
                }
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    Task task = snap.getValue(Task.class);
                    tasks.add(task);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        objectiveDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (objectives!=null) {
                    objectives.clear();
                }
                for (DataSnapshot snap: dataSnapshot.getChildren())
                {
                    Objective objective = snap.getValue(Objective.class);
                    objective.setId(snap.getKey());
                    try{
                        objective.setComplete(snap.child("isComplete").getValue(boolean.class));
                    }
                    catch (Exception e){
                        objective.setComplete(false);
                    }

                    if (objective == null){
                        System.out.println( "error reading object collection");
                    }
                    else objectives.add(objective);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    */
}

