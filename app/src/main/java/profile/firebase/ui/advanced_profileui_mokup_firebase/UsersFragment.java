package profile.firebase.ui.advanced_profileui_mokup_firebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mauro on 06/01/2018.
 */

public class UsersFragment extends Fragment {

  private RecyclerView mRecyclerUsers;
  private RecyclerView.LayoutManager mManagerLayout;
  private FirebaseFirestore mFireStore;
  private AdapterRecyclerUsers mAdapter;
  private List<ModelProfile>mListUsers;


  public UsersFragment() {
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_users,container,false);
    mFireStore = FirebaseFirestore.getInstance();
    mRecyclerUsers = view.findViewById(R.id.recyclerviewUsers);
    mManagerLayout = new LinearLayoutManager(view.getContext());
    mRecyclerUsers.setLayoutManager(mManagerLayout);
    mRecyclerUsers.setHasFixedSize(true);
    mListUsers = new ArrayList<>();
    mAdapter = new AdapterRecyclerUsers(view.getContext(),mListUsers);
    mRecyclerUsers.setAdapter(mAdapter);

    return view;
  }

  @Override public void onStart() {
    super.onStart();

    /*
    for info look firebase reference https://firebase.google.com/docs/firestore/query-data/listen
     */

    mFireStore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
      @Override public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

        if (e != null){
          Log.w("Error ",e);
          return;
        }

        for (DocumentChange doc:documentSnapshots.getDocumentChanges()) {

          if (doc.getType() == DocumentChange.Type.ADDED){

            ModelProfile modelProfile = doc.getDocument().toObject(ModelProfile.class);
            mListUsers.add(modelProfile);
            mAdapter.notifyDataSetChanged();
          }
        }
      }
    });
  }
}
