class AddForeignKey < ActiveRecord::Migration
  def change
    execute("ALTER TABLE members ADD FOREIGN KEY (user_id) REFERENCES users(id) ")
    execute("ALTER TABLE members ADD FOREIGN KEY (tour_id) REFERENCES tours(id) ")
    execute("ALTER TABLE invitations ADD FOREIGN KEY (user_id) REFERENCES users(id) ")
    execute("ALTER TABLE invitations ADD FOREIGN KEY (member_id) REFERENCES members(id) ")
  end
end
