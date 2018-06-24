package tigerstyle.social.com.banggiaxe.customize.SectionRecycleView;

interface ItemProvider {

  int getSectionCount();

  int getItemCount(int sectionIndex);

  boolean showHeadersForEmptySections();
}
