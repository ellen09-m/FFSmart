package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminActivity extends AppCompatActivity {

    private Button btnAddUser, btnRegister;
    private EditText inputName, inputEmail;
    private LinearLayout containerAddUser;
    private ListView userList;
    private UserAdapter adapter;
    private List<User> users;
    private boolean isAdmin;
    private String selectedRole = null;

    private ImageView hamburgerIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Get the isAdmin flag from the intent
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);

        // Initialize views
        btnAddUser = findViewById(R.id.btn_add_user);
        btnRegister = findViewById(R.id.btn_register);
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        containerAddUser = findViewById(R.id.container_add_user);
        userList = findViewById(R.id.user_list);
        hamburgerIcon = findViewById(R.id.hamburger_icon);

        hamburgerIcon.setOnClickListener(v -> showPopupMenu(v));

        // Retrieve the user's name from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String firstName = sharedPreferences.getString("firstName", "UserName"); // Default to "UserName" if not found
        String lastName = sharedPreferences.getString("lastName", ""); // Default to empty if not found
        String fullName = firstName + " " + lastName; // Combine first name and last name

        // Find the TextView where the profile name is displayed
        TextView profileNameTextView = findViewById(R.id.userNameTextView);
        profileNameTextView.setText(fullName); // Set the user's full name

        // Initialize user list and adapter
        users = new ArrayList<>();
        adapter = new UserAdapter(this, R.layout.user_item, users);
        userList.setAdapter(adapter);

        btnAddUser.setOnClickListener(v -> toggleAddUserView());
        btnRegister.setOnClickListener(v -> registerUser());

        // Hide add user button if not admin
        if (!isAdmin) {
            btnAddUser.setVisibility(View.GONE);
        }
    }

    private void toggleAddUserView() {
        if (isAdmin) {
            containerAddUser.setVisibility(
                    containerAddUser.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE
            );
        } else {
            Toast.makeText(this, "You do not have permission to add users", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser() {
        if (!isAdmin) {
            Toast.makeText(this, "You do not have permission to add users", Toast.LENGTH_SHORT).show();
            return;
        }

        String userName = inputName.getText().toString().trim();
        String userEmail = inputEmail.getText().toString().trim();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Please enter Name and Email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show role selection dialog every time
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select User Role")
                .setItems(new String[]{"Kitchen Staff", "Chef"}, (dialog, which) -> {
                    String selectedRole = (which == 0) ? "Kitchen Staff" : "Chef";

                    // Generate the unique ID based on the selected role
                    String prefix = (selectedRole.equals("Kitchen Staff")) ? "S" : "A";
                    String uniqueId = prefix + UUID.randomUUID().toString().substring(0, 5).toUpperCase();

                    // Create and add the new user
                    User newUser = new User(userName, uniqueId, userEmail);
                    users.add(newUser);
                    adapter.notifyDataSetChanged();
                    sendEmail(userEmail, userName, uniqueId);

                    Toast.makeText(AdminActivity.this, "User added: " + userName + " (" + uniqueId + ")", Toast.LENGTH_SHORT).show();
                    inputName.setText("");
                    inputEmail.setText("");
                    containerAddUser.setVisibility(View.GONE);
                })
                .create()
                .show();
    }

    private void showRoleSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select User Role")
                .setItems(new String[]{"Kitchen Staff", "Chef"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Set the selected role (either Kitchen Staff or Chef)
                        selectedRole = (which == 0) ? "Kitchen Staff" : "Chef";

                        // Now that the role is selected, register the user
                        registerUser();
                    }
                })
                .create()
                .show();
    }


    private void sendEmail(String email, String name, String uniqueId) {
        String subject = "Welcome to FFSmart";
        String message = "Hello " + name + ",\n\n" +
                "You have been registered to FFSmart. Your unique ID is: " + uniqueId + ".\n" +
                "Please use this ID to log in.\n\n" +
                "Best regards,\nFFSmart Team";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private class UserAdapter extends ArrayAdapter<User> {
        private final Context context;
        private final int resource;

        public UserAdapter(Context context, int resource, List<User> users) {
            super(context, resource, users);
            this.context = context;
            this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(resource, parent, false);
            }

            User user = getItem(position);

            ImageView profilePic = convertView.findViewById(R.id.profile_pic);
            TextView userName = convertView.findViewById(R.id.user_name);
            TextView userId = convertView.findViewById(R.id.user_id);
            ImageButton btnDelete = convertView.findViewById(R.id.btn_delete);

            profilePic.setImageResource(R.drawable.ic_profile);
            userName.setText(user.getName());
            userId.setText(user.getUniqueId());

            btnDelete.setOnClickListener(v -> deleteUser(position));

            return convertView;
        }

        private void deleteUser(int position) {
            if (isAdmin) {
                User removedUser = users.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "User deleted: " + removedUser.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "You do not have permission to delete users", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class User {
        private final String name;
        private final String uniqueId;
        private final String email;

        public User(String name, String uniqueId, String email) {
            this.name = name;
            this.uniqueId = uniqueId;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public String getEmail() {
            return email;
        }
    }

    // Handle profile click
    public void onProfileClick(View view) {
        Intent intent = new Intent(AdminActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    // Handle settings click
    public void onSettingsClick(View view) {
        Intent intent = new Intent(AdminActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_dashboard) {
                startActivity(new Intent(AdminActivity.this, DashboardActivity.class));
                return true;

            } else if (itemId == R.id.menu_settings) {
                startActivity(new Intent(AdminActivity.this, SettingsActivity.class));
                return true;

            } else if (itemId == R.id.menu_admin) {
                startActivity(new Intent(AdminActivity.this, AdminActivity.class));
                return true;

            } else if (itemId == R.id.menu_inventory) {
                startActivity(new Intent(AdminActivity.this, InventoryActivity.class));
                return true;
            } else if (itemId == R.id.menu_alerts) {
                startActivity(new Intent(AdminActivity.this, MaintenanceActivity.class));
                return true;
            } else if (itemId == R.id.menu_delivery) {
                startActivity(new Intent(AdminActivity.this, DeliveryActivity.class));
                return true;
            } else if (itemId == R.id.menu_reorder) {
                startActivity(new Intent(AdminActivity.this, ReorderActivity.class));
                return true;
            } else if (itemId == R.id.menu_health) {
                startActivity(new Intent(AdminActivity.this, HealthSafetyActivity.class));
                return true;
            } else if (itemId == R.id.menu_profile) {
                startActivity(new Intent(AdminActivity.this, ProfileActivity.class));
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }
}