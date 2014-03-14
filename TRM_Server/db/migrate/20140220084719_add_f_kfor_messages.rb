class AddFKforMessages < ActiveRecord::Migration
  def change
    execute("ALTER TABLE messages ADD FOREIGN KEY (user_id) REFERENCES users(id) ")
  end
end
