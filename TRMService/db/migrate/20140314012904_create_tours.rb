class CreateTours < ActiveRecord::Migration
  def change
    create_table :tours do |t|
      t.string :title, :limit => 100
      t.string :destination, :limit => 100
      t.float :adult_price
      t.float :child_price
      t.date :depature_date
      t.date :return_date
      t.integer :manager_id, :null => false
      t.integer :max_members
      t.text :description
      t.string :currency, :limit =>5
      t.timestamps
    end

  end
end
