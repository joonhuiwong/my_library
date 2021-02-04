package com.joonhuiwong.mylibrary.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.joonhuiwong.mylibrary.R;
import com.joonhuiwong.mylibrary.activities.AllBooksActivity;
import com.joonhuiwong.mylibrary.activities.BookActivity;
import com.joonhuiwong.mylibrary.db.entity.BookEntity;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    private static final String TAG = "BookAdapter";
    private final Context mContext;
    private final String parentActivity;
    private List<BookEntity> books = new ArrayList<>();

    public BookAdapter(Context mContext, String parentActivity) {
        this.mContext = mContext;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_book, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.textBookName.setText(books.get(position).getName());

        if (books.get(position).getImageUrl().isEmpty()) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(R.mipmap.booklogo_mini)
                    .into(holder.imgBook);
        } else {
            Glide.with(mContext)
                    .asBitmap()
                    .load(books.get(position).getImageUrl())
                    .into(holder.imgBook);
        }

        holder.parent.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, BookActivity.class);
            intent.putExtra(BookActivity.BOOK_ID_KEY, books.get(position).getId());
            mContext.startActivity(intent);
        });

        holder.txtAuthor.setText(books.get(position).getAuthor());
        holder.txtDescription.setText(books.get(position).getShortDesc());

        if (books.get(position).isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelativeLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);

            switch (parentActivity) {
                case AllBooksActivity.ACTIVITY_NAME:
                    holder.btnDelete.setVisibility(View.GONE);
                    break;
                    /*
                case AlreadyReadBookActivity.ACTIVITY_NAME:
                    holder.btnDelete.setVisibility(View.VISIBLE);
                    holder.btnDelete.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete " + books.get(position).getName() + "?");
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            if (Utils.getInstance(mContext).removeFromAlreadyRead(books.get(position))) {
                                Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged(); //TODO: Enhance with Callback Interface after learning about it.
                            } else {
                                Toast.makeText(mContext, "Something wrong happened, Please try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("No", (dialog, which) -> {

                        });
                        builder.create().show();
                    });
                    break;
                case CurrentlyReadingBookActivity.ACTIVITY_NAME:
                    holder.btnDelete.setVisibility(View.VISIBLE);
                    holder.btnDelete.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete " + books.get(position).getName() + "?");
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            if (Utils.getInstance(mContext).removeFromCurrentlyReading(books.get(position))) {
                                Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged(); //TODO: Enhance with Callback Interface after learning about it.
                            } else {
                                Toast.makeText(mContext, "Something wrong happened, Please try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("No", (dialog, which) -> {

                        });
                        builder.create().show();
                    });
                    break;
                case FavoriteBookActivity.ACTIVITY_NAME:
                    holder.btnDelete.setVisibility(View.VISIBLE);
                    holder.btnDelete.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete " + books.get(position).getName() + "?");
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            if (Utils.getInstance(mContext).removeFromFavorite(books.get(position))) {
                                Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged(); //TODO: Enhance with Callback Interface after learning about it.
                            } else {
                                Toast.makeText(mContext, "Something wrong happened, Please try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("No", (dialog, which) -> {

                        });
                        builder.create().show();
                    });
                    break;
                case WantToReadBookActivity.ACTIVITY_NAME:
                    holder.btnDelete.setVisibility(View.VISIBLE);
                    holder.btnDelete.setOnClickListener(v -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete " + books.get(position).getName() + "?");
                        builder.setPositiveButton("Yes", (dialog, which) -> {
                            if (Utils.getInstance(mContext).removeFromWantToRead(books.get(position))) {
                                Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged(); //TODO: Enhance with Callback Interface after learning about it.
                            } else {
                                Toast.makeText(mContext, "Something wrong happened, Please try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("No", (dialog, which) -> {

                        });
                        builder.create().show();
                    });
                    break;
                     */
            }
        } else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelativeLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
        notifyDataSetChanged(); //TODO Can replace with something better when more knowledgeable
    }

    public BookEntity getBookAt(int position) {
        return books.get(position);
    }

    public class BookHolder extends RecyclerView.ViewHolder {
        private final CardView parent;
        private final ImageView imgBook;
        private final ImageView downArrow;
        private final ImageView upArrow;
        private final TextView textBookName;
        private final TextView btnDelete;

        private final RelativeLayout expandedRelativeLayout;
        private final TextView txtAuthor;
        private final TextView txtDescription;

        public BookHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            imgBook = itemView.findViewById(R.id.imgBook);
            textBookName = itemView.findViewById(R.id.textBookName);

            btnDelete = itemView.findViewById(R.id.btnDelete);

            downArrow = itemView.findViewById(R.id.btnDownArrow);
            upArrow = itemView.findViewById(R.id.btnUpArrow);
            expandedRelativeLayout = itemView.findViewById(R.id.expandedRelativeLayout);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtDescription = itemView.findViewById(R.id.txtShortDescription);

            downArrow.setOnClickListener(v -> {
                BookEntity book = books.get(getAdapterPosition());
                book.setExpanded(!book.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });

            upArrow.setOnClickListener(v -> {
                BookEntity book = books.get(getAdapterPosition());
                book.setExpanded(!book.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
