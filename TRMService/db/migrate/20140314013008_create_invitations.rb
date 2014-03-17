class CreateInvitations < ActiveRecord::Migration
  def change
    create_table :invitations do |t|
      t.integer :user_id, :null => false
      t.integer :member_id, :null => false
      t.integer :type, :limit => 4, :null => false
      t.text :description
      t.integer :status, :limit => 4
      t.timestamps
    end
  end
end
