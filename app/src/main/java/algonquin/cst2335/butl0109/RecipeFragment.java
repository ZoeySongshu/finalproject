package algonquin.cst2335.butl0109;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.butl0109.databinding.RecipeDetailsBinding;


public class RecipeFragment extends Fragment {
    RecipeActivity selected;

    public RecipeFragment(RecipeActivity d) {
        selected = d;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        RecipeDetailsBinding binding = RecipeDetailsBinding.inflate(inflater);

        binding.textView2.setText(selected.getTitle());


        return binding.getRoot();
    }
}
