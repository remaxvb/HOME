class AddForeignKey < ActiveRecord::Migration
  def up
    #add a foreign key
    execute <<-SQL
          ALTER TABLE members
            ADD CONSTRAINT member_fk_user_id
            FOREIGN KEY (user_id)
            REFERENCES users(id)
    SQL
    execute <<-SQL
          ALTER TABLE members
            ADD CONSTRAINT member_fk_tour_id
            FOREIGN KEY (tour_id)
            REFERENCES tours(id)
    SQL
    execute <<-SQL
          ALTER TABLE tours
            ADD CONSTRAINT tour_fk_manager_id
            FOREIGN KEY (manager_id)
            REFERENCES users(id)
    SQL
    execute <<-SQL
          ALTER TABLE invitations
            ADD CONSTRAINT invitation_fk_user_id
            FOREIGN KEY (user_id)
            REFERENCES users(id)
    SQL
    execute <<-SQL
          ALTER TABLE invitations
            ADD CONSTRAINT invitation_fk_member_id
            FOREIGN KEY (member_id)
            REFERENCES members(id)
    SQL
  end

  def down
    # reversible
    execute <<-SQL
            ALTER TABLE invitations
              DROP FOREIGN KEY invitation_fk_member_id
    SQL
    execute <<-SQL
            ALTER TABLE invitations
              DROP FOREIGN KEY invitation_fk_user_id
    SQL
    execute <<-SQL
            ALTER TABLE tours
              DROP FOREIGN KEY tour_fk_manager_id
    SQL
    execute <<-SQL
            ALTER TABLE members
              DROP FOREIGN KEY member_fk_user_id
    SQL
    execute <<-SQL
            ALTER TABLE members
              DROP FOREIGN KEY member_fk_tour_id
    SQL
  end

end
