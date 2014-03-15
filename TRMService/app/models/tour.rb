class Tour < ActiveRecord::Base
  has_many :members, dependent: :destroy
  belongs_to :user, foreign_key: "manager_id"
  validate :associated_user

  #Custom validate check whether manager_id exists or not
  def associated_user
    if self.manager_id.nil?
      errors.add(:manager_id, "can not blank")
    elsif User.find_by_id(self.manager_id).nil?
      errors.add(:manager, "does not exist")
    end
  end
end