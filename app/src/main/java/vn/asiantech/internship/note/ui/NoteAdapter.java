package vn.asiantech.internship.note.ui;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.note.model.Note;

/**
 * Created by at-dinhvo on 19/06/2017.
 */
class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> mNotes;
    private OnItemClickListener mOnItemClickListener;

    NoteAdapter(List<Note> data, OnItemClickListener onItemClickListener) {
        mNotes = data;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.mTvNoteTitle.setText(note.getTitle());
        holder.mTvNoteContent.setText(note.getContent());
        holder.mTvDateTime.setText(note.getDatetime());
        if (note.getPath() != null) {
            holder.mImgNote.setImageURI(Uri.parse(note.getPath()));
        }
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    /**
     * create RecyclerViewHolder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvNoteTitle;
        private TextView mTvNoteContent;
        private TextView mTvDateTime;
        private ImageView mImgNote;

        ViewHolder(View itemView) {
            super(itemView);
            mTvNoteTitle = (TextView) itemView.findViewById(R.id.tvItemNoteTitle);
            mTvNoteContent = (TextView) itemView.findViewById(R.id.tvItemNoteContent);
            mTvDateTime = (TextView) itemView.findViewById(R.id.tvDateTime);
            mImgNote = (ImageView) itemView.findViewById(R.id.imgItemNote);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(mNotes.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    /**
     * send position
     */
    interface OnItemClickListener {
        void onClick(int pos);
    }
}
