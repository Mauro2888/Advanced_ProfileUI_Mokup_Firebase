package profile.firebase.ui.advanced_profileui_mokup_firebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;

/**
 * Created by Mauro on 06/01/2018.
 */

public class AdapterRecyclerUsers extends RecyclerView.Adapter<AdapterRecyclerUsers.ViewHolderData> {
  Context context;
  List<ModelProfile>modelProfilesList;

  public AdapterRecyclerUsers(Context context, List<ModelProfile> modelProfilesList) {
    this.context = context;
    this.modelProfilesList = modelProfilesList;
  }

  @Override public ViewHolderData onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_users,parent,false);

    return new ViewHolderData(view);
  }

 @Override public void onBindViewHolder(ViewHolderData holder, int position) {

    ModelProfile modelProfile = modelProfilesList.get(position);

    holder.nameUser.setText(modelProfile.getName());
    Glide.with(context).load(modelProfile.getImage()).into(holder.circleImageView);

  }

  @Override public int getItemCount() {
    return modelProfilesList.size();
  }

  public class ViewHolderData extends RecyclerView.ViewHolder {
    CircleImageView circleImageView;
    TextView nameUser;
    public ViewHolderData(View itemView) {
      super(itemView);
      circleImageView = itemView.findViewById(R.id.profile_image_reycler);
      nameUser = itemView.findViewById(R.id.profile_name_reycler);
    }
  }
}
