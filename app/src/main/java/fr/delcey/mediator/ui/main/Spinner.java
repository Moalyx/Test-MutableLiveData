package fr.delcey.mediator.ui.main;

import android.view.View;
import android.widget.AdapterView;

public interface Spinner {
    void onItemSelected(AdapterView<?> parent, View view, int position, long id);
}
