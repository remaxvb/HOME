class CreateMembers < ActiveRecord::Migration
  def change
    create_table :members do |t|
      t.integer :user_id, :null => false
      t.integer :tour_id, :null => false
      t.integer :group_id
      t.integer :status, :limit => 4
      t.float :longtitude
      t.float :lattitude
    end
    add_index :members, [:user_id, :tour_id], :unique => true
  end
end
