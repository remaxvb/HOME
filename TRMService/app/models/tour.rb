class Tour < ActiveRecord::Base
  has_many :members
  validates :id_manager, presence: true
end