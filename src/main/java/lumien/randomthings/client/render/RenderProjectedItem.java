package lumien.randomthings.client.render;

import java.util.Random;

import lumien.randomthings.entitys.EntityProjectedItem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderProjectedItem extends Render<EntityProjectedItem>
{
	private final RenderItem itemRenderer;
	private final Random random = new Random();

	public RenderProjectedItem(RenderManager renderManagerIn, RenderItem p_i46167_2_)
	{
		super(renderManagerIn);
		this.itemRenderer = p_i46167_2_;
		this.shadowSize = 0.15F;
		this.shadowOpaque = 0.75F;
	}

	private int transformModelCount(EntityProjectedItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_)
	{
		ItemStack itemstack = itemIn.getItem();
		Item item = itemstack.getItem();

		if (item == null)
		{
			return 0;
		}
		else
		{
			boolean flag = p_177077_9_.isGui3d();
			int i = this.getModelCount(itemstack);
			float f = 0.25F;
			float f2 = p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;

			float modY = (float) p_177077_4_ + f2 * 0.25f;

			if (flag)
			{
				modY -= 0.15f;
			}
			else
			{
				modY -= 0.2;
			}

			GlStateManager.translate((float) p_177077_2_, modY, (float) p_177077_6_);

			if (flag || this.renderManager.options != null)
			{
				float f3 = ((itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * (180F / (float) Math.PI);
				GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
			}

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			return i;
		}
	}

	protected int getModelCount(ItemStack stack)
	{
		int i = 1;

		if (stack.getCount() > 48)
		{
			i = 5;
		}
		else if (stack.getCount() > 32)
		{
			i = 4;
		}
		else if (stack.getCount() > 16)
		{
			i = 3;
		}
		else if (stack.getCount() > 1)
		{
			i = 2;
		}

		return i;
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	@Override
	public void doRender(EntityProjectedItem entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		ItemStack itemstack = entity.getItem();
		int i;

		if (!itemstack.isEmpty() && itemstack.getItem() != null)
		{
			i = Item.getIdFromItem(itemstack.getItem()) + itemstack.getMetadata();
		}
		else
		{
			i = 187;
		}

		this.random.setSeed(i);
		boolean flag = false;

		if (this.bindEntityTexture(entity))
		{
			this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).setBlurMipmap(false, false);
			flag = true;
		}

		GlStateManager.enableRescaleNormal();
		GlStateManager.alphaFunc(516, 0.1F);
		GlStateManager.enableBlend();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.pushMatrix();
		IBakedModel ibakedmodel = this.itemRenderer.getItemModelWithOverrides(itemstack, entity.world, (EntityLivingBase) null);
		int j = this.transformModelCount(entity, x, y, z, partialTicks, ibakedmodel);
		boolean flag1 = ibakedmodel.isGui3d();

		if (!flag1)
		{
			float f3 = -0.0F * (j - 1) * 0.5F;
			float f4 = -0.0F * (j - 1) * 0.5F;
			float f5 = -0.09375F * (j - 1) * 0.5F;
			GlStateManager.translate(f3, f4, f5);
		}

		if (this.renderOutlines)
		{
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		for (int k = 0; k < j; ++k)
		{
			if (flag1)
			{
				GlStateManager.pushMatrix();

				if (k > 0)
				{
					float f7 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
					float f9 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
					float f6 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
					GlStateManager.translate(shouldSpreadItems() ? f7 : 0, shouldSpreadItems() ? f9 : 0, f6);
				}

				ibakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
				this.itemRenderer.renderItem(itemstack, ibakedmodel);
				GlStateManager.popMatrix();
			}
			else
			{
				GlStateManager.pushMatrix();

				if (k > 0)
				{
					float f8 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
					float f10 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
					GlStateManager.translate(f8, f10, 0.0F);
				}

				ibakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
				this.itemRenderer.renderItem(itemstack, ibakedmodel);
				GlStateManager.popMatrix();
				GlStateManager.translate(0.0F, 0.0F, 0.09375F);
			}
		}

		if (this.renderOutlines)
		{
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableBlend();
		this.bindEntityTexture(entity);

		if (flag)
		{
			this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).restoreLastBlurMipmap();
		}

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityProjectedItem entity)
	{
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

	/*
	 * ==================================== FORGE START
	 * ===========================================
	 */

	/**
	 * Items should spread out when rendered in 3d?
	 * 
	 * @return
	 */
	public boolean shouldSpreadItems()
	{
		return true;
	}

	/**
	 * Items should have a bob effect
	 * 
	 * @return
	 */
	public boolean shouldBob()
	{
		return true;
	}
	/*
	 * ==================================== FORGE END
	 * =============================================
	 */
}