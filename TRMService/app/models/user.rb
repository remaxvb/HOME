class User < ActiveRecord::Base
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable
  has_many :tours, dependent: :destroy

  def self.search(search)
    if search
      params=search.split(" ")
      params.each do |param|
        collection=find(:all, :conditions => ['firstname LIKE ? OR lastname LIKE ? OR email LIKE ?', "%#{param}%", "%#{param}%", "%#{param}%"])
        collections=Array.new
        collections.push(collection)
      end
    results=[]
      collections.each do |obj|
        if results.map(&:id).include? obj.id #check if the name has been seen already
          obj.destroy!
        else
          results << obj #if not, add it to the seen array
        end
      end
    end
    return results
  end

end
