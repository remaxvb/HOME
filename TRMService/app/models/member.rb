class Member < ActiveRecord::Base
  belongs_to :user
  belongs_to :tour
  has_many :invitations
end
