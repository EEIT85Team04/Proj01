package iiiedu.sirius.proj01;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import iiiedu.sirius.proj01.model.DetailMealVO;
import iiiedu.sirius.proj01.model.MealMenuVO;
import iiiedu.sirius.proj01.model.MealVO;

public class MealDetailDialogFragment extends DialogFragment {
    MyMealExpandableAdapter adapter;
    LayoutInflater inflater;
    View rootView;
    private Integer mealposition;
    ExpandableListView expandableList;
    private MealVO[] meallist;

    public interface MealInputListener {
        void onMealInputComplete(MealVO meal);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        TextView mealname = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mealposition = AppController.getInstance().getMealposition();
        meallist = AppController.getInstance().getMeallist();
        // Get the layout inflater
        inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.mealprompts, null);
        mealname = (TextView) rootView.findViewById(R.id.mealname);
        mealname.setText(meallist[mealposition].getMeal_name());
        expandableList = (ExpandableListView)rootView.findViewById(R.id.mealExpandableListView);
        expandableList.setDividerHeight(2);
        expandableList.setClickable(true);
        adapter = new MyMealExpandableAdapter(meallist[mealposition].getMealmenus());
        adapter.setInflater(inflater);
        expandableList.setAdapter(adapter);
        expandableList.setGroupIndicator(null);
        int count = adapter.getGroupCount();
        for (int position = 1; position <= count; position++)       //開啟所有子元素
            expandableList.expandGroup(position - 1);
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true; // This way the expander cannot be collapsed
            }
        });
        builder.setView(rootView)
                // Add action buttons
               .setNegativeButton("取消", null)
               .setNeutralButton("清除",new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which){}
               })
               .setPositiveButton("確認",new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which){}
               });

        return builder.create();
    }

    @Override
    public void onStart()
    {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button neutralButton = (Button) d.getButton(Dialog.BUTTON_NEUTRAL);
            neutralButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    adapter = new MyMealExpandableAdapter(meallist[mealposition].getMealmenus());
                    adapter.setInflater(inflater);
                    expandableList.setAdapter(adapter);
                    int count = adapter.getGroupCount();
                    for (int position = 1; position <= count; position++)       //開啟所有子元素
                        expandableList.expandGroup(position - 1);
                }
            });

            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean wantToCloseDialog = true;
                    //Do stuff, possibly set wantToCloseDialog to true then...
                    MealVO meal = meallist[mealposition];
                    for(MealMenuVO childtem : meal.getMealmenus()){
                        wantToCloseDialog = wantToCloseDialog && childtem.checkquantity();
                    }
                    if(wantToCloseDialog) {
                        MealInputListener listener = (MealInputListener) getActivity();
                        listener.onMealInputComplete(meal);
                        d.dismiss();
                    }
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                }
            });
        }
    }
}
